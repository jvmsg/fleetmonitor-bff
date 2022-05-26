package br.com.ddmx.fleetmonitor.bff.api.requests.legado;

import br.com.ddmx.fleetmonitor.bff.dto.model.SessaoDTO;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class GetAccountLoginRequestWS implements Serializable {

    public LinkedHashMap<String, Object> getLegadoRequest(SessaoDTO sessaoDTO) {
        LinkedHashMap<String, Object> request = new LinkedHashMap<>();
        request.put("conta", sessaoDTO.getConta());

        return request;
    }
}
