package br.com.ddmx.fleetmonitor.bff.api.requests.legado;

import br.com.ddmx.fleetmonitor.bff.clientws.embbeded.OptionsWS;

import java.io.Serializable;

public class GetRankingMotoristasRequestWS implements Serializable {
    private OptionsWS options;

    public OptionsWS getOptions() {
        return options;
    }

    public void setOptions(OptionsWS options) {
        this.options = options;
    }
}
