package org.gabrielbarrilli.motelteste.mapper.queryMotel.service;

import org.gabrielbarrilli.motelteste.mapper.queryMotel.QueryMotelMapper;
import org.gabrielbarrilli.motelteste.mapper.queryMotel.model.QueryEntradaQuarto;
import org.gabrielbarrilli.motelteste.mapper.queryMotel.response.QueryEntradaQuartoResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class QueryEntradaQuartoService {

    private final QueryEntradaQuarto queryEntradaQuarto;

    private final JdbcTemplate jdbcTemplate;

    public QueryEntradaQuartoService(QueryEntradaQuarto queryEntradaQuarto, JdbcTemplate jdbcTemplate) {
        this.queryEntradaQuarto = queryEntradaQuarto;
        this.jdbcTemplate = jdbcTemplate;
    }

    public QueryEntradaQuartoResponse buscaEntradaQuartoPorId(Long id) {
        StringBuilder query = new StringBuilder();

        query.append(queryEntradaQuarto.queryEntradaQuarto(id));
        return jdbcTemplate.queryForObject(query.toString(), QueryMotelMapper.rowMapperEntradaQuarto);
    }
}
