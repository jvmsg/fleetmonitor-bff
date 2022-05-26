package br.com.ddmx.fleetmonitor.bff.dao.impl;

import br.com.ddmx.careca.boot.commons.utils.Util;
import br.com.ddmx.careca.boot.database.dao.base.DAOImpl;
import br.com.ddmx.fleetmonitor.bff.dao.interfaces.ISessaoDAO;
import br.com.ddmx.fleetmonitor.bff.dto.filter.SessaoFilterDTO;
import br.com.ddmx.fleetmonitor.bff.dto.model.SessaoDTO;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class SessaoDAO extends DAOImpl<SessaoDTO, SessaoFilterDTO> implements ISessaoDAO {
    @Override
    protected Document createQuery(SessaoFilterDTO sessaoFilterDTO) {
        Document query = new Document();
        Document usuario = new Document();
        List<Document> or = new ArrayList<>();

        if (!Util.isNullOrEmpty(sessaoFilterDTO.getLogin()) || !Util.isNullOrEmpty(sessaoFilterDTO.getConta())) {
            if (!Util.isNullOrEmpty(sessaoFilterDTO.getLogin())) {
                usuario.append("login", sessaoFilterDTO.getLogin());
            }
            if (!Util.isNullOrEmpty(sessaoFilterDTO.getConta())) {
                usuario.append("conta", sessaoFilterDTO.getConta());
            }
            or.add(usuario);
        }
        if (!Util.isNullOrEmpty(sessaoFilterDTO.getTokens()))
            or.add(new Document("$in", sessaoFilterDTO.getTokens()));

        if (!Util.isNullOrEmpty(or))
            query.put("$or", or);

        return query;
    }

    @Override
    protected void createIndexes() {
        cachedCollection.createIndex(new Document("token", 1), new IndexOptions().unique(true));
    }

    @Override
    protected boolean isCached() {
        return false;
    }

    @Override
    protected String getDatasourceName() {
        return "principal";
    }

    @Override
    public SessaoDTO newInstanceDTO() {
        return new SessaoDTO();
    }
}
