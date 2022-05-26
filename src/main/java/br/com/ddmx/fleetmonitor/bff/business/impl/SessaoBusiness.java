package br.com.ddmx.fleetmonitor.bff.business.impl;

import br.com.ddmx.careca.boot.business.base.BaseBusiness;
import br.com.ddmx.careca.boot.commons.connections.ConnectElasticSearch;
import br.com.ddmx.careca.boot.commons.datasources.DataSourceFactory;
import br.com.ddmx.careca.boot.commons.utils.ExceptionPrinter;
import br.com.ddmx.careca.boot.commons.utils.TrataInput;
import br.com.ddmx.careca.boot.commons.utils.Util;
import br.com.ddmx.careca.boot.database.dao.factory.DAOFactory;
import br.com.ddmx.fleetmonitor.bff.api.requests.legado.GetIndicadoresMesRequestWS;
import br.com.ddmx.fleetmonitor.bff.api.requests.legado.GetRankingMotoristasRequestWS;
import br.com.ddmx.fleetmonitor.bff.communs.MonitorBFFStatics;
import br.com.ddmx.fleetmonitor.bff.dto.model.SessaoDTO;
import br.com.ddmx.fleetmonitor.bff.business.interfaces.ISessaoBusiness;
import br.com.ddmx.fleetmonitor.bff.clientws.filter.SessaoFilterWS;
import br.com.ddmx.fleetmonitor.bff.clientws.model.SessaoWS;
import br.com.ddmx.fleetmonitor.bff.dao.interfaces.ISessaoDAO;
import br.com.ddmx.fleetmonitor.bff.dto.filter.SessaoFilterDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.time.FastDateFormat;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.*;
import java.util.stream.Collectors;

public class SessaoBusiness extends BaseBusiness<SessaoDTO, SessaoFilterDTO, SessaoWS, SessaoFilterWS, ISessaoDAO> implements ISessaoBusiness {
    private static ConnectElasticSearch elasticSearch = DataSourceFactory.getInstance().getElasticsearchInstance("principal");
    private static String elasticIndexLimiteVelocidade = MonitorBFFStatics.ETL_INDEX.LIMETE_VELOCIDADE.getNome();
    private static FastDateFormat fdfDataHora = FastDateFormat.getInstance("dd/MM/YYYY HH:mm:ss");
    private static FastDateFormat fdfData = FastDateFormat.getInstance("dd/MM/YYYY");

    @Override
    protected ISessaoDAO getMyDAO() {
        return DAOFactory.getDAOFactoryImpl().getMyDAO(ISessaoDAO.class);
    }

    @Override
    public SessaoFilterDTO newInstanceFilter() {
        return new SessaoFilterDTO();
    }

    @Override
    public SessaoWS newInstanceWS() {
        return new SessaoWS();
    }

    @Override
    public boolean save(SessaoDTO obj) throws JsonProcessingException {
        SessaoFilterDTO filtro =  newInstanceFilter();
        filtro.setConta(obj.getConta());
        filtro.setLogin(obj.getLogin());
        filtro.setLimit(1);

        try {
            List<SessaoDTO> sessaoDTOList = list(filtro);
            if (!Util.isNullOrEmpty(sessaoDTOList)){
                obj.setId(sessaoDTOList.get(0).getId());
                return super.update(obj);
            }
        } catch (Exception e){
            logger.error("Erro ao verificar se o usuário \"" + obj.getLogin() + "\" da conta \"" + obj.getConta() + "\" já estava loggado - " + ExceptionPrinter.getInstance().print(e));
            return false;
        }
        return super.save(obj);
    }


    @Override
    public LinkedHashMap<String, Object> convertWStoLegado(GetRankingMotoristasRequestWS requestWS, SessaoDTO sessaoDTO) {
        LinkedHashMap<String, Object> request = new LinkedHashMap<>();
        LinkedHashMap<String, Object> mapOptions = new LinkedHashMap<>();

        mapOptions.put("conta", sessaoDTO.getConta());
        mapOptions.put("dataMin", requestWS.getOptions().getDataMin());
        mapOptions.put("dataMax", requestWS.getOptions().getDataMax());
        mapOptions.put("grupos", new ArrayList<>(requestWS.getOptions().getGrupos().keySet()));
        request.put("options", mapOptions);

        return request;
    }

    @Override
    public SearchResponse searchSourceBuilder(String sessao, String dataMin, String dataMax) throws Exception {
        SessaoDTO sessaoDTO = getByUniqueKey("token", sessao);

        TermQueryBuilder contaFilter = new TermQueryBuilder("conta.keyword", sessaoDTO.getConta());

        if (dataMax == null){
            dataMax = fdfDataHora.format(new Date(Long.MAX_VALUE));
        }
        if (dataMin == null){
            dataMin = fdfDataHora.format(new Date(Long.MIN_VALUE));
        }

        List<Date> datas = TrataInput.separaDias(fdfDataHora.parse(dataMin), fdfDataHora.parse(dataMax), false);

        TermsQueryBuilder dataFilter = new TermsQueryBuilder(
                "data.keyword",
                datas.stream()
                        .map((Date dia)->fdfData.format(dia))
                        .collect(Collectors.toList())
        );

        List<QueryBuilder> filters = new ArrayList<>();
        filters.add(dataFilter);
        filters.add(contaFilter);

        BoolQueryBuilder query = new BoolQueryBuilder().filter(contaFilter).filter(dataFilter);

        TermsAggregationBuilder aggs = new TermsAggregationBuilder("grupos")
                .size(1000)
                .field("grupo.keyword")
                .subAggregation(new TermsAggregationBuilder("subgrupos")
                        .size(1000)
                        .field("subgrupo.keyword")
                        .subAggregation(new TermsAggregationBuilder("operadores")
                                .size(1000)
                                .field("motorista.keyword"))
                        .subAggregation(new TermsAggregationBuilder("equipamentos")
                                .size(1000)
                                .field("placa.keyword"))
                );



        return elasticSearch.search(elasticIndexLimiteVelocidade, new SearchSourceBuilder().query(query).aggregation(aggs).size(0));
    }

    @Override
    public SearchResponse searchSourceBuilder(String sessao, String dataMin, String dataMax, Map<String, List<String>> grupos) throws Exception {
        return null;
    }

    @Override
    public LinkedHashMap<String, Object> convertWStoLegado(GetIndicadoresMesRequestWS requestWS, SessaoDTO sessaoDTO) {
        LinkedHashMap<String, Object> request = new LinkedHashMap<>();
        LinkedHashMap<String, Object> mapOptions = new LinkedHashMap<>();

        mapOptions.put("conta", sessaoDTO.getConta());
        mapOptions.put("dataMin", requestWS.getOptions().getDataMin());
        mapOptions.put("dataMax", requestWS.getOptions().getDataMax());
        mapOptions.put("grupos", new ArrayList<>(requestWS.getOptions().getGrupos().keySet()));
        request.put("options", mapOptions);

        return request;
    }
}
