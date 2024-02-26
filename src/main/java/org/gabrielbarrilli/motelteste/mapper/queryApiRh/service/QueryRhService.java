package org.gabrielbarrilli.motelteste.mapper.queryApiRh.service;

import org.gabrielbarrilli.motelteste.mapper.queryApiRh.QueryMapper;
import org.gabrielbarrilli.motelteste.mapper.queryApiRh.model.QueryRhModel;
import org.gabrielbarrilli.motelteste.mapper.queryApiRh.response.QueryCodigoServidorResponse;
import org.gabrielbarrilli.motelteste.mapper.queryApiRh.response.QueryMatriculaNomeCpfResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class QueryRhService {

    private final QueryRhModel queryRhModel;

    private final JdbcTemplate jdbcTemplate;

    public QueryRhService(QueryRhModel queryRhModel, JdbcTemplate jdbcTemplate) {
        this.queryRhModel = queryRhModel;
        this.jdbcTemplate = jdbcTemplate;
    }

    public QueryCodigoServidorResponse buscaServidorPorId(Integer id) {
        StringBuilder query = new StringBuilder();

        query.append(queryRhModel.queryCodigoServidor(id));
        return jdbcTemplate.queryForObject(query.toString(), QueryMapper.rowMapperCodigoServidor);
    }

    public QueryMatriculaNomeCpfResponse buscaPorMatriculaNomeCpf(String cpf, String nome, String matricula) {
        StringBuilder query = new StringBuilder();

        query.append(queryRhModel.queryMatriculaNomeCpf(cpf, nome, matricula));
        return jdbcTemplate.queryForObject(query.toString(), QueryMapper.rowMapperMatriculaNomeCpf);
    }
}
