package org.gabrielbarrilli.motelteste.mapper.query.service;

import org.gabrielbarrilli.motelteste.mapper.QueryMapper;
import org.gabrielbarrilli.motelteste.mapper.query.model.QueryEntradaQuarto;
import org.gabrielbarrilli.motelteste.mapper.query.QueryEntradaQuartoResponse;
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
        return jdbcTemplate.queryForObject(query.toString(), QueryMapper.rowMapperEntradaQuarto);
    }
}
