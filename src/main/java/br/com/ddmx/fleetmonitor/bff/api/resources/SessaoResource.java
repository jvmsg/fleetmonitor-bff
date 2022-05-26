package br.com.ddmx.fleetmonitor.bff.api.resources;

import br.com.ddmx.careca.boot.api.annotation.InterceptRequest;
import br.com.ddmx.careca.boot.api.resources.base.BaseWSResource;
import br.com.ddmx.careca.boot.business.factory.BusinessFactory;
import br.com.ddmx.careca.boot.clientws.response.NotificationResponseWS;
import br.com.ddmx.careca.boot.commons.utils.ExceptionPrinter;
import br.com.ddmx.careca.boot.commons.utils.GeraChave;
import br.com.ddmx.fleetmonitor.bff.api.annotations.LoggedAuthorization;
import br.com.ddmx.fleetmonitor.bff.api.requests.GetSegurancaRequestWS;
import br.com.ddmx.fleetmonitor.bff.api.requests.RealizaLoginRequestWS;
import br.com.ddmx.fleetmonitor.bff.api.requests.RealizaLogoutRequestWS;
import br.com.ddmx.fleetmonitor.bff.api.requests.legado.*;
import br.com.ddmx.fleetmonitor.bff.api.validators.SessaoFilterWSValidator;
import br.com.ddmx.fleetmonitor.bff.api.validators.SessaoWSValidator;
import br.com.ddmx.fleetmonitor.bff.business.interfaces.ISessaoBusiness;
import br.com.ddmx.fleetmonitor.bff.clientws.embbeded.OptionsWS;
import br.com.ddmx.fleetmonitor.bff.clientws.filter.SessaoFilterWS;
import br.com.ddmx.fleetmonitor.bff.clientws.model.SessaoWS;
import br.com.ddmx.fleetmonitor.bff.dto.filter.SessaoFilterDTO;
import br.com.ddmx.fleetmonitor.bff.dto.model.SessaoDTO;
import br.com.ddmx.nodecontroller.utils.SntpProtocol;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Path("sessao")
@InterceptRequest
@Api(tags = {"Sessão"}, authorizations = {
        @Authorization(value = "login", scopes = {}),
        @Authorization(value = "key", scopes = {})})
public class SessaoResource extends BaseWSResource<SessaoDTO, SessaoFilterDTO, SessaoWS, SessaoFilterWS, SessaoWSValidator, SessaoFilterWSValidator, ISessaoBusiness> {
    private static LegadoResource legado = new LegadoResource();

    private String geraToken(RealizaLoginRequestWS request) throws JsonProcessingException {
        String dataAtual = String.valueOf(SntpProtocol.getInstance().getGMTDate().getTime());
        String obj = mapper.writeValueAsString(request) + dataAtual;

        return GeraChave.geraChave(obj);
    }

    @Override
    protected ISessaoBusiness getMyBusiness() {
        return BusinessFactory.getBusinessFactoryImpl().getMyBusiness(ISessaoBusiness.class);
    }

    @POST
    @Path("json/login")
    public NotificationResponseWS<LinkedHashMap<String, Object>> login(RealizaLoginRequestWS request, @Context HttpServletRequest req, @Context HttpServletResponse res) {
        LinkedHashMap<String, Object> payloadLegado;
        LinkedHashMap<String, Object> responseLegado;
        NotificationResponseWS<LinkedHashMap<String, Object>> response = new NotificationResponseWS<>();
        response.setSucesso(false);
        try {
            payloadLegado = mapper.convertValue(request, LinkedHashMap.class);
            responseLegado = legado.login(payloadLegado, req);
            response.setSucesso((boolean) responseLegado.get("status"));
            if (response.isSucesso()) {
                responseLegado.remove("status");
                responseLegado.remove("Sucesso");
                SessaoDTO sessaoDTO = new SessaoDTO();
                sessaoDTO.setLogin(request.getLogin());
                sessaoDTO.setConta(request.getConta().toUpperCase());
                // sessaoDTO.setSenha(GeraChave.geraChave(request.getSenha()));
                sessaoDTO.setSenha(request.getSenha()); // Senha não está sendo salva por GeraChave por ainda utilizar sistema legado
                sessaoDTO.setToken(geraToken(request));
                if (getMyBusiness().save(sessaoDTO)) {
                    responseLegado.put("sessao", sessaoDTO.getToken());
                    response.setRetorno(responseLegado);
                }
                else {
                    response.setSucesso(false);
                    response.setRetorno(null);
                    logger.error("Falha ao criar sessão para usuário o usuário \"" + request.getLogin() + "\" da conta \"" + request.getConta() + "\".");
                    response.setMensagem("Falha ao criar sessão para usuário o usuário \"" + request.getLogin() + "\" da conta \"" + request.getConta() + "\".");
                }
            }

        } catch (JsonProcessingException e) {
            logger.error("Erro ao gerar token da sessão - " + ExceptionPrinter.getInstance().print(e));
            internalServerErrorException("Erro ao gerar token da sessão - " + ExceptionPrinter.getInstance().print(e));
        }catch (Exception e) {
            logger.error("Erro ao realizar login no servidor Legado - " + ExceptionPrinter.getInstance().print(e));
            internalServerErrorException("Erro ao realizar login no servidor Legado - " + ExceptionPrinter.getInstance().print(e));
        }
        return response;
    }

    @POST
    @Path("json/logout")
    @LoggedAuthorization
    public NotificationResponseWS<?> logout(RealizaLogoutRequestWS request, @Context HttpServletRequest req, @Context HttpServletResponse res){
        NotificationResponseWS<?> response = new NotificationResponseWS<>();
        ISessaoBusiness iSessaoBusiness = BusinessFactory.getBusinessFactoryImpl().getMyBusiness(ISessaoBusiness.class);
        String sessao = req.getHeader("sessao");
        try {
            SessaoWS sessaoWS = iSessaoBusiness.converteDTO2WS(iSessaoBusiness.getByUniqueKey("token", sessao));
            if (sessaoWS != null) {
                logger.info("Sessão \"" + sessao + "\" encontrada no banco.");
                super.remove(sessaoWS, req);
                response.setSucesso(true);
                response.setMensagem("Logout da sessão \"" + sessao + "\" realizado com sucesso.");
            } else {
                response.setSucesso(true);
                response.setMensagem("Sessão \"" + sessao + "\" não encontrada no sistema.");
                logger.info("Sessão \"" + sessao + "\" não encontrada no sistema.");
            }
            logger.info("Logout da sessão \"" + sessao + "\" realizado com sucesso.");
        } catch (Exception e) {
            logger.error("Erro ao realizar logout: " + ExceptionPrinter.getInstance().print(e));
            internalServerErrorException("Erro ao realizar logout: " + ExceptionPrinter.getInstance().print(e));
        }
        return response;
    }

    @POST
    @Path("json/ativaAlarme")
    @LoggedAuthorization
    public LinkedHashMap<String, Object> ativaAlarme(AtivaAlarmeRequestWS request, @Context HttpServletRequest req) {
        LinkedHashMap<String, Object> retorno = new LinkedHashMap<>();
        try{
            String sessao = req.getHeader("sessao");
            SessaoDTO sessaoDTO = getMyBusiness().getByUniqueKey("token", sessao);

            retorno = legado.ativaAlarme(request.getLegadoRequest(sessaoDTO), req);
        } catch (Exception e){
            logger.error(ExceptionPrinter.getInstance().print(e));
            retorno.put("mensagem", ExceptionPrinter.getInstance().print(e));
            retorno.put("Sucesso", false);
        }
        return retorno;
    }

    @POST
    @Path("json/atualizaVeiculo")
    @LoggedAuthorization
    public NotificationResponseWS<?> atualizaVeiculo(AtualizaVeiculoRequestWS request, @Context HttpServletRequest req, @Context HttpServletResponse res) {
        ISessaoBusiness iSessaoBusiness = BusinessFactory.getBusinessFactoryImpl().getMyBusiness(ISessaoBusiness.class);
        String sessao = req.getHeader("sessao");
        NotificationResponseWS response = new NotificationResponseWS();
        try {
            SessaoDTO sessaoDTO = iSessaoBusiness.getByUniqueKey("token", sessao);
            LinkedHashMap<String, Object> legadoRequest = new LinkedHashMap<>();
            legadoRequest.put("conta", sessaoDTO.getConta());
            legadoRequest.put("login", sessaoDTO.getLogin());
            legadoRequest.put("senha", sessaoDTO.getSenha());
            legadoRequest.put("serial_veiculo", request.getSerial_veiculo());
            LinkedHashMap<String, Object> legadoResponse = legado.atualizaVeiculo(legadoRequest, req);
            response.setSucesso((Boolean) legadoResponse.get("Sucesso"));
            legadoResponse.remove("success");
            legadoResponse.remove("Sucesso");
            response.setRetorno(legadoResponse);
        } catch (Exception e) {
            logger.error("Erro ao atualizar veiculo: " + request.getSerial_veiculo() + " - " + ExceptionPrinter.getInstance().print(e));
            internalServerErrorException("Erro ao atualizar veiculo: " + request.getSerial_veiculo() + " - " + ExceptionPrinter.getInstance().print(e));
        }
        return response;
    }

    @POST
    @Path("json/listaAvisos")
    @LoggedAuthorization
    public NotificationResponseWS<?> listaAvisos(AtualizaVeiculoRequestWS request, @Context HttpServletRequest req, @Context HttpServletResponse res) {
        ISessaoBusiness iSessaoBusiness = BusinessFactory.getBusinessFactoryImpl().getMyBusiness(ISessaoBusiness.class);
        String sessao = req.getHeader("sessao");
        NotificationResponseWS response = new NotificationResponseWS();
        try {
            SessaoDTO sessaoDTO = iSessaoBusiness.getByUniqueKey("token", sessao);
            LinkedHashMap<String, Object> legadoRequest = new LinkedHashMap<>();
            legadoRequest.put("conta", sessaoDTO.getConta());
            legadoRequest.put("login", sessaoDTO.getLogin());
            legadoRequest.put("senha", sessaoDTO.getSenha());
            legadoRequest.put("serial_veiculo", request.getSerial_veiculo());
            LinkedHashMap<String, Object> legadoResponse = legado.listaAvisos(legadoRequest, req);
            response.setSucesso((Boolean) legadoResponse.get("Sucesso"));
            legadoResponse.remove("success");
            legadoResponse.remove("Sucesso");
            response.setRetorno(legadoResponse);
        } catch (Exception e) {
            logger.error("Erro ao atualizar veiculo: " + request.getSerial_veiculo() + " - " + ExceptionPrinter.getInstance().print(e));
            internalServerErrorException("Erro ao atualizar veiculo: " + request.getSerial_veiculo() + " - " + ExceptionPrinter.getInstance().print(e));
        }
        return response;
    }

    @POST
    @Path("json/listaAreas")
    @LoggedAuthorization
    public NotificationResponseWS<?> listaAreas(AtualizaVeiculoRequestWS request, @Context HttpServletRequest req, @Context HttpServletResponse res) {
        ISessaoBusiness iSessaoBusiness = BusinessFactory.getBusinessFactoryImpl().getMyBusiness(ISessaoBusiness.class);
        String sessao = req.getHeader("sessao");
        NotificationResponseWS response = new NotificationResponseWS();
        try {
            SessaoDTO sessaoDTO = iSessaoBusiness.getByUniqueKey("token", sessao);
            LinkedHashMap<String, Object> legadoRequest = new LinkedHashMap<>();
            legadoRequest.put("conta", sessaoDTO.getConta());
            legadoRequest.put("login", sessaoDTO.getLogin());
            legadoRequest.put("senha", sessaoDTO.getSenha());
            legadoRequest.put("serial_veiculo", request.getSerial_veiculo());
            LinkedHashMap<String, Object> legadoResponse = legado.listaAreas(legadoRequest, req);
            response.setSucesso((Boolean) legadoResponse.get("Sucesso"));
            legadoResponse.remove("success");
            legadoResponse.remove("Sucesso");
            response.setRetorno(legadoResponse);
        } catch (Exception e) {
            logger.error("Erro ao atualizar veiculo: " + request.getSerial_veiculo() + " - " + ExceptionPrinter.getInstance().print(e));
            internalServerErrorException("Erro ao atualizar veiculo: " + request.getSerial_veiculo() + " - " + ExceptionPrinter.getInstance().print(e));
        }
        return response;
    }

    @POST
    @Path("json/listaRotas")
    @LoggedAuthorization
    public LinkedHashMap<String, Object> listaRotas(ListaRotasRequestWS request, @Context HttpServletRequest req) {
        LinkedHashMap<String, Object> retorno = new LinkedHashMap<>();
        try{
            String sessao = req.getHeader("sessao");
            SessaoDTO sessaoDTO = getMyBusiness().getByUniqueKey("token", sessao);

            retorno = legado.listaRotas(request.getLegadoRequest(sessaoDTO), req);
            retorno.put("Sucesso", true);
        } catch (Exception e){
            logger.error(ExceptionPrinter.getInstance().print(e));
            retorno.put("mensagem", ExceptionPrinter.getInstance().print(e));
            retorno.put("Sucesso", false);
        }
        return retorno;
    }

    @POST
    @Path("json/getAccountLogin")
    @LoggedAuthorization
    public LinkedHashMap<String, Object> getAccountLogin(GetAccountLoginRequestWS request, @Context HttpServletRequest req) {
        LinkedHashMap<String, Object> retorno = new LinkedHashMap<>();
        try{
            String sessao = req.getHeader("sessao");
            SessaoDTO sessaoDTO = getMyBusiness().getByUniqueKey("token", sessao);

            retorno = legado.getAccountLogin(request.getLegadoRequest(sessaoDTO), req);
            retorno.put("Sucesso", true);
        } catch (Exception e){
            logger.error(ExceptionPrinter.getInstance().print(e));
            retorno.put("mensagem", ExceptionPrinter.getInstance().print(e));
            retorno.put("Sucesso", false);
        }
        return retorno;
    }

    @POST
    @Path("json/get-ranking-motoristas")
    @LoggedAuthorization
    public LinkedHashMap<String, Object> getRankingMotoristas(GetRankingMotoristasRequestWS request, @Context HttpServletRequest req) {
        LinkedHashMap<String, Object> retorno = new LinkedHashMap<>();
        try{
            String sessao = req.getHeader("sessao");
            SessaoDTO sessaoDTO = getMyBusiness().getByUniqueKey("token", sessao);

            retorno = legado.getRankingMotoristas(getMyBusiness().convertWStoLegado(request, sessaoDTO), req);
            retorno.put("Sucesso", true);
        } catch (Exception e){
            logger.error(ExceptionPrinter.getInstance().print(e));
            retorno.put("mensagem", ExceptionPrinter.getInstance().print(e));
            retorno.put("Sucesso", false);
        }
        return retorno;
    }

    @POST
    @Path("json/get-indicadores-mes")
    @LoggedAuthorization
    public LinkedHashMap<String, Object> getIndicadoresMes(GetIndicadoresMesRequestWS request, @Context HttpServletRequest req) {
        LinkedHashMap<String, Object> retorno = new LinkedHashMap<>();
        try{
            String sessao = req.getHeader("sessao");
            SessaoDTO sessaoDTO = getMyBusiness().getByUniqueKey("token", sessao);

            retorno = legado.getRankingMotoristas(getMyBusiness().convertWStoLegado(request, sessaoDTO), req);
            retorno.put("Sucesso", true);
        } catch (Exception e){
            logger.error(ExceptionPrinter.getInstance().print(e));
            retorno.put("mensagem", ExceptionPrinter.getInstance().print(e));
            retorno.put("Sucesso", false);
        }
        return retorno;
    }

    @GET
    @Path("json/get-grupos")
    @LoggedAuthorization
    public LinkedHashMap<String, Object> getGrupos(@Context HttpServletRequest req) {
        LinkedHashMap<String, Object> retorno = new LinkedHashMap<>();
        try{
            retorno = legado.getGrupos(req);
            retorno.put("Sucesso", true);
        } catch (Exception e){
            logger.error(ExceptionPrinter.getInstance().print(e));
            retorno.put("mensagem", ExceptionPrinter.getInstance().print(e));
            retorno.put("Sucesso", false);
        }
        return retorno;
    }

    @POST
    @Path("json/get-seguranca")
    @LoggedAuthorization
    public NotificationResponseWS<Map<String, Object>> getSeguranca(GetSegurancaRequestWS request, @Context HttpServletRequest req){

        String sessao = req.getHeader("sessao");
        OptionsWS options = request.getOptions();
        String dataMax = options.getDataMax();
        String dataMin = options.getDataMin();
        LinkedHashMap<String, List<String>> grupos =  options.getGrupos();

        try {
            if (grupos == null)
                getMyBusiness().searchSourceBuilder(sessao, dataMin, dataMax);
            else
                getMyBusiness().searchSourceBuilder(sessao, dataMin, dataMax, grupos);
        } catch (Exception e){
            logger.error("Erro ao gerar filtro para ElasticSearch - " + ExceptionPrinter.getInstance().print(e));
            internalServerErrorException("Erro ao gerar filtro para ElasticSearch - " + ExceptionPrinter.getInstance().print(e));
        }



        return null;
    }
}