package br.com.ddmx.fleetmonitor.bff.api.requests.legado;

import br.com.ddmx.fleetmonitor.bff.clientws.embbeded.OptionsWS;

public class GetIndicadoresMesRequestWS {
    private OptionsWS options;

    public OptionsWS getOptions() {
        return options;
    }

    public void setOptions(OptionsWS options) {
        this.options = options;
    }

}
