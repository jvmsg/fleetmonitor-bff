package br.com.ddmx.fleetmonitor.bff.api.providers;

import br.com.ddmx.careca.boot.business.factory.BusinessFactory;
import br.com.ddmx.careca.boot.clientws.response.NotificationResponseWS;
import br.com.ddmx.careca.boot.commons.utils.Util;
import br.com.ddmx.fleetmonitor.bff.api.annotations.LoggedAuthorization;
import br.com.ddmx.fleetmonitor.bff.business.interfaces.ISessaoBusiness;
import br.com.ddmx.fleetmonitor.bff.dto.model.SessaoDTO;
import br.com.ddmx.nodecontroller.utils.ExceptionPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Priority(10)
@LoggedAuthorization
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoggedInterceptor implements ContainerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    private final ISessaoBusiness iSessaoBusiness = BusinessFactory.getBusinessFactoryImpl().getMyBusiness(ISessaoBusiness.class);

    @Override
    public void filter(ContainerRequestContext context)  {
        String sessao = context.getHeaders().getFirst("sessao");
        SessaoDTO sessaoDTO;
        if (Util.isNullOrEmpty(sessao))
            throw new BadRequestException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(new NotificationResponseWS<>(false, "Header \"sessao\" deve estar preenchido."))
                            .build()
            );
        try {
            sessaoDTO = iSessaoBusiness.getByUniqueKey("token", sessao);
        } catch (IOException e) {
            logger.error("Erro ao buscar sessao \"" + sessao + "\" no banco: " + ExceptionPrinter.getInstance().print(e));

            throw new InternalServerErrorException(
                    Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(new NotificationResponseWS<>("Erro ao buscar sessao \"" + sessao + "\" no banco: " + ExceptionPrinter.getInstance().print(e)))
                            .build()
            );
        } catch (Exception e) {
            logger.error("Erro no LoggedInterceptor - " + ExceptionPrinter.getInstance().print(e));
            throw new InternalServerErrorException(
                    Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("Erro no LoggedInterceptor - " + ExceptionPrinter.getInstance().print(e))
                            .build()
            );
        }
        if (sessaoDTO == null) {
            logger.error("Sessão \"" + sessao + "\" não encontrada no banco.");
            throw new ForbiddenException(
                    Response.status(Response.Status.FORBIDDEN)
                            .entity(new NotificationResponseWS<>(false, "Sessão \"" + sessao + "\" não encontrada no banco."))
                            .build()
            );
        }
    }
}
