package br.com.ddmx.fleetmonitor.bff.api.resources;

import br.com.ddmx.careca.boot.api.annotation.InterceptRequest;
import br.com.ddmx.careca.boot.commons.model.configs.app.AppConfigBuilder;
import br.com.ddmx.careca.boot.commons.utils.ExceptionPrinter;
import br.com.fulltimeonline.clientws.api.utils.HttpMethodsHandler;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

@Path("legado")
@InterceptRequest
@Api(tags = {"Legado"}, authorizations = {
        @Authorization(value = "login", scopes = {}),
        @Authorization(value = "key", scopes = {})})
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LegadoResource implements Serializable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private final ObjectMapper mapper = (new ObjectMapper()).setSerializationInclusion(JsonInclude.Include.NON_NULL).configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    private static final HashMap<String, String> configBuilder = AppConfigBuilder.getInstance().getMapaConfiguracoes();
    private static final String url_old_monitor_bff = configBuilder.get("url_old_monitor_bff");

    @POST
    @Path("json/efetuaLogin")
    public LinkedHashMap<String, Object> login(LinkedHashMap<String, Object> request, @Context HttpServletRequest req) {
        LinkedHashMap<String, Object> retorno = new LinkedHashMap<>();
        try{
            String responseStr = HttpMethodsHandler.doJSONPost(url_old_monitor_bff + "/efetuaLogin", mapper.writeValueAsString(request));
            retorno = mapper.readValue(responseStr, LinkedHashMap.class);
            retorno.put("Sucesso", true);
        } catch (Exception e){
            logger.error(ExceptionPrinter.getInstance().print(e));
            retorno.put("mensagem", ExceptionPrinter.getInstance().print(e));
            retorno.put("Sucesso", false);
        }
        return retorno;
    }

    @POST
    @Path("json/ativaAlarme")
    public LinkedHashMap<String, Object> ativaAlarme(LinkedHashMap<String, Object> request, @Context HttpServletRequest req) {
        LinkedHashMap<String, Object> retorno = new LinkedHashMap<>();
        try{
            String responseStr = HttpMethodsHandler.doJSONPost(url_old_monitor_bff + "/ativaAlarme", mapper.writeValueAsString(request));
            retorno = mapper.readValue(responseStr, LinkedHashMap.class);
            retorno.put("Sucesso", true);
        } catch (Exception e){
            logger.error(ExceptionPrinter.getInstance().print(e));
            retorno.put("mensagem", ExceptionPrinter.getInstance().print(e));
            retorno.put("Sucesso", false);
        }
        return retorno;
    }

    @POST
    @Path("json/atualizaVeiculo")
    public LinkedHashMap<String, Object> atualizaVeiculo(LinkedHashMap<String, Object> request, @Context HttpServletRequest req) {
        LinkedHashMap<String, Object> retorno = new LinkedHashMap<>();
        try{
            String responseStr = HttpMethodsHandler.doJSONPost(url_old_monitor_bff + "/atualizaVeiculo", mapper.writeValueAsString(request));
            retorno = mapper.readValue(responseStr, LinkedHashMap.class);
            retorno.put("Sucesso", true);
        } catch (Exception e){
            logger.error(ExceptionPrinter.getInstance().print(e));
            retorno.put("mensagem", ExceptionPrinter.getInstance().print(e));
            retorno.put("Sucesso", false);
        }
        return retorno;
    }

    @POST
    @Path("json/listaAvisos")
    public LinkedHashMap<String, Object> listaAvisos(LinkedHashMap<String, Object> request, @Context HttpServletRequest req) {
        LinkedHashMap<String, Object> retorno = new LinkedHashMap<>();
        try{
            String responseStr = HttpMethodsHandler.doJSONPost(url_old_monitor_bff + "/listaAvisos", mapper.writeValueAsString(request));
            retorno = mapper.readValue(responseStr, LinkedHashMap.class);
            retorno.put("Sucesso", true);
        } catch (Exception e){
            logger.error(ExceptionPrinter.getInstance().print(e));
            retorno.put("mensagem", ExceptionPrinter.getInstance().print(e));
            retorno.put("Sucesso", false);
        }
        return retorno;
    }

    @POST
    @Path("json/listaAreas")
    public LinkedHashMap<String, Object> listaAreas(LinkedHashMap<String, Object> request, @Context HttpServletRequest req) {
        LinkedHashMap<String, Object> retorno = new LinkedHashMap<>();
        try{
            String responseStr = HttpMethodsHandler.doJSONPost(url_old_monitor_bff + "/listaAreas", mapper.writeValueAsString(request));
            retorno = mapper.readValue(responseStr, LinkedHashMap.class);
            retorno.put("Sucesso", true);
        } catch (Exception e){
            logger.error(ExceptionPrinter.getInstance().print(e));
            retorno.put("mensagem", ExceptionPrinter.getInstance().print(e));
            retorno.put("Sucesso", false);
        }
        return retorno;
    }

    @POST
    @Path("json/listaRotas")
    public LinkedHashMap<String, Object> listaRotas(LinkedHashMap<String, Object> request, @Context HttpServletRequest req) {
        LinkedHashMap<String, Object> retorno = new LinkedHashMap<>();
        try{
            String responseStr = HttpMethodsHandler.doJSONPost(url_old_monitor_bff + "/listaRotas", mapper.writeValueAsString(request));
            retorno = mapper.readValue(responseStr, LinkedHashMap.class);
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
    public LinkedHashMap<String, Object> getAccountLogin(LinkedHashMap<String, Object> request, @Context HttpServletRequest req) {
        LinkedHashMap<String, Object> retorno = new LinkedHashMap<>();
        try{
            String responseStr = HttpMethodsHandler.doJSONPost(url_old_monitor_bff + "/getAccountLogin", mapper.writeValueAsString(request));
            retorno = mapper.readValue(responseStr, LinkedHashMap.class);
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
    public LinkedHashMap<String, Object> getRankingMotoristas(LinkedHashMap<String, Object> request, @Context HttpServletRequest req) {
        LinkedHashMap<String, Object> retorno = new LinkedHashMap<>();
        try{
            String responseStr = HttpMethodsHandler.doJSONPost(url_old_monitor_bff + "/modules/bi/ranking-motoristas/get-ranking-motoristas", mapper.writeValueAsString(request));
            retorno = mapper.readValue(responseStr, LinkedHashMap.class);
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
    public LinkedHashMap<String, Object> getIndicadoresMes(LinkedHashMap<String, Object> request, @Context HttpServletRequest req) {
        LinkedHashMap<String, Object> retorno = new LinkedHashMap<>();
        try{
            String responseStr = HttpMethodsHandler.doJSONPost(url_old_monitor_bff + "/modules/bi/indicadores-mes/get-indicadores-mes", mapper.writeValueAsString(request));
            retorno = mapper.readValue(responseStr, LinkedHashMap.class);
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
    public LinkedHashMap<String, Object> getGrupos(@Context HttpServletRequest req) {
        LinkedHashMap<String, Object> retorno = new LinkedHashMap<>();
        try{
            String responseStr = HttpMethodsHandler.doGet(url_old_monitor_bff + "/modules/bi/grupos/get-grupos/GERDAUCHARQUEADAS");
            retorno = mapper.readValue(responseStr, LinkedHashMap.class);
            retorno.put("Sucesso", true);
        } catch (Exception e){
            logger.error(ExceptionPrinter.getInstance().print(e));
            retorno.put("mensagem", ExceptionPrinter.getInstance().print(e));
            retorno.put("Sucesso", false);
        }
        return retorno;
    }

}
