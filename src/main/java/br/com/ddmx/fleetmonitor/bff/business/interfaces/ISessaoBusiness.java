package br.com.ddmx.fleetmonitor.bff.business.interfaces;

import br.com.ddmx.careca.boot.business.interfaces.IBusiness;
import br.com.ddmx.fleetmonitor.bff.api.requests.legado.GetIndicadoresMesRequestWS;
import br.com.ddmx.fleetmonitor.bff.api.requests.legado.GetRankingMotoristasRequestWS;
import br.com.ddmx.fleetmonitor.bff.dto.model.SessaoDTO;
import br.com.ddmx.fleetmonitor.bff.clientws.filter.SessaoFilterWS;
import br.com.ddmx.fleetmonitor.bff.clientws.model.SessaoWS;
import br.com.ddmx.fleetmonitor.bff.dto.filter.SessaoFilterDTO;
import org.elasticsearch.action.search.SearchResponse;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface ISessaoBusiness extends IBusiness<SessaoDTO, SessaoFilterDTO, SessaoWS, SessaoFilterWS> {
    LinkedHashMap<String, Object> convertWStoLegado(GetRankingMotoristasRequestWS requestWS, SessaoDTO sessaoDTO);
    LinkedHashMap<String, Object> convertWStoLegado(GetIndicadoresMesRequestWS requestWS, SessaoDTO sessaoDTO);
    SearchResponse searchSourceBuilder(String sessao, String dataMin, String dataMax) throws Exception;
    SearchResponse searchSourceBuilder(String sessao, String dataMin, String dataMax, Map<String, List<String>> grupos) throws Exception;
}
