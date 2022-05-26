package br.com.ddmx.fleetmonitor.bff.api.requests.legado;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AtualizaVeiculoRequestWS {
    @NotNull(message = "Campo \"serial_veiculo\" não pode ser nulo.")
    @NotEmpty(message = "Campo \"serial_veiculo\" não pode ser vazio.")
    private String serial_veiculo;

    public String getSerial_veiculo() {
        return serial_veiculo;
    }

    public void setSerial_veiculo(String serial_veiculo) {
        this.serial_veiculo = serial_veiculo;
    }
}
