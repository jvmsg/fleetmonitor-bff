package br.com.ddmx.fleetmonitor.bff.api.validators;

import br.com.ddmx.careca.boot.api.validators.RequestValidator;
import br.com.ddmx.careca.boot.commons.utils.ExceptionPrinter;
import br.com.ddmx.fleetmonitor.bff.api.requests.GetSegurancaRequestWS;
import br.com.ddmx.fleetmonitor.bff.clientws.embbeded.OptionsWS;
import org.apache.commons.lang3.time.FastDateFormat;

import javax.ws.rs.core.MultivaluedMap;
import java.text.ParseException;

public class GetSegurancaRequestWSValidator extends RequestValidator<GetSegurancaRequestWS> {
    private static FastDateFormat fdf = FastDateFormat.getInstance("DD/MM/YYYY HH:mm:SS");
    @Override
    protected void validaRequest(GetSegurancaRequestWS getSegurancaRequestWS, MultivaluedMap<String, String> multivaluedMap) {
        OptionsWS options = getSegurancaRequestWS.getOptions();
        if (options != null) {
            String dataMin = options.getDataMin();
            String dataMax = options.getDataMax();

            if (dataMin != null || dataMax != null) {
                if (dataMin != null) {
                    try {
                        fdf.parse(dataMin);
                    } catch (ParseException e) {
                        logger.error("Erro com o formato do campo dataMin - " + ExceptionPrinter.getInstance().print(e));
                        badRequestException("Erro com o formato do campo dataMin - " + ExceptionPrinter.getInstance().print(e));
                    }
                }

                if (dataMax != null) {
                    try {
                        fdf.parse(dataMax);
                    } catch (ParseException e) {
                        logger.error("Erro com o formato do campo dataMax - " + ExceptionPrinter.getInstance().print(e));
                        badRequestException("Erro com o formato do campo dataMax - " + ExceptionPrinter.getInstance().print(e));
                    }
                }
            } else {
                badRequestException("Pelo menos um campo dataMin ou dataMax deve estar preenchido.");
            }

        } else {
            badRequestException("Campo options deve estar preenchido.");
        }
    }
}
