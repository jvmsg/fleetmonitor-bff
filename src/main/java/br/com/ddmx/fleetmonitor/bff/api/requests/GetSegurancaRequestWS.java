package br.com.ddmx.fleetmonitor.bff.api.requests;

import br.com.ddmx.fleetmonitor.bff.clientws.embbeded.OptionsWS;

import java.io.Serializable;

public class GetSegurancaRequestWS implements Serializable {
    private OptionsWS options;

    public OptionsWS getOptions() {
        return options;
    }

    public void setOptions(OptionsWS options) {
        this.options = options;
    }
}
