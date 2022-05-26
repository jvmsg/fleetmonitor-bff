package br.com.ddmx.fleetmonitor.bff.api.requests.legado;

import br.com.ddmx.fleetmonitor.bff.dto.model.SessaoDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.LinkedHashMap;

public class ListaRotasRequestWS implements Serializable {
    @NotNull(message = "Campo \"idUsuario\" não pode ser nulo.")
    @NotEmpty(message = "Campo \"idUsuario\" não pode ser vazio.")
    private String idUsuario;
    @NotNull(message = "Campo \"diaBusca\" não pode ser nulo.")
    @NotEmpty(message = "Campo \"diaBusca\" não pode ser vazio.")
    @JsonFormat(pattern = "DD/MM/YYYY HH:mm:SS")
    private String diaBusca;

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDiaBusca() {
        return diaBusca;
    }

    public void setDiaBusca(String diaBusca) {
        this.diaBusca = diaBusca;
    }

    public LinkedHashMap<String, Object> getLegadoRequest(SessaoDTO sessaoDTO) {
        LinkedHashMap<String, Object> request = new LinkedHashMap<>();
        request.put("conta", sessaoDTO.getConta());
        request.put("login", sessaoDTO.getLogin());
        request.put("senha", sessaoDTO.getSenha());
        request.put("idUsuario", idUsuario);
        request.put("diaBusca", diaBusca);

        return request;
    }
}
