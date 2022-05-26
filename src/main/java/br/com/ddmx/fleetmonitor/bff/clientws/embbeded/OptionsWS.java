package br.com.ddmx.fleetmonitor.bff.clientws.embbeded;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashMap;
import java.util.List;

public class OptionsWS {
    private String dataMin;
    private String dataMax;
    private LinkedHashMap<String, List<String>> grupos;

    public String getDataMin() {
        return dataMin;
    }

    public void setDataMin(String dataMin) {
        this.dataMin = dataMin;
    }

    public String getDataMax() {
        return dataMax;
    }

    public void setDataMax(String dataMax) {
        this.dataMax = dataMax;
    }

    public LinkedHashMap<String, List<String>> getGrupos() {
        return grupos;
    }

    public void setGrupos(LinkedHashMap<String, List<String>> grupos) {
        this.grupos = grupos;
    }
}
