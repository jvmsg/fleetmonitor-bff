package br.com.ddmx.fleetmonitor.bff.api.requests.legado;

import br.com.ddmx.fleetmonitor.bff.dto.model.SessaoDTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedHashMap;

public class AtivaAlarmeRequestWS implements Serializable {
    @NotNull(message = "Campo \"serial_veiculo\" deve ser uma String.")
    @NotEmpty(message = "Campo \"serial_veiculo\" não pode ser vazio.")
    private String serial_veiculo;

    @NotNull(message = "Campo \"alarme_ativo\" deve ser um Boolean.")
    private Boolean alarme_ativo;

    public String getSerial_veiculo() {
        return serial_veiculo;
    }

    public void setSerial_veiculo(String serial_veiculo) {
        this.serial_veiculo = serial_veiculo;
    }

    public Boolean getAlarme_ativo() {
        return alarme_ativo;
    }

    public void setAlarme_ativo(Boolean alarme_ativo) {
        this.alarme_ativo = alarme_ativo;
    }

    public LinkedHashMap<String, Object> getLegadoRequest(SessaoDTO sessaoDTO) {
        LinkedHashMap<String, Object> request = new LinkedHashMap<>();

        request.put("serial_veiculo", serial_veiculo);
        request.put("alarme_ativo", alarme_ativo);
        request.put("conta", sessaoDTO.getConta());
        request.put("login", sessaoDTO.getLogin());
        request.put("senha", sessaoDTO.getSenha());

        return request;
    }
}
