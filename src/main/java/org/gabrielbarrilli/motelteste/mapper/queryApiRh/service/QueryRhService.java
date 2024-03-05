package org.gabrielbarrilli.motelteste.mapper.queryApiRh.service;

import org.gabrielbarrilli.motelteste.mapper.queryApiRh.QueryMapper;
import org.gabrielbarrilli.motelteste.mapper.queryApiRh.model.QueryRhModel;
import org.gabrielbarrilli.motelteste.mapper.queryApiRh.response.QueryCodigoServidorResponse;
import org.gabrielbarrilli.motelteste.mapper.queryApiRh.response.QueryMatriculaNomeCpfResponse;
import org.gabrielbarrilli.motelteste.mapper.queryApiRh.response.QueryServidorRelatorioResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public Page<QueryMatriculaNomeCpfResponse> buscaPorMatriculaNomeCpf(String cpf, String nome, String matricula, Pageable pageable) {

        StringBuilder query = new StringBuilder();

        query.append(queryRhModel.queryMatriculaNomeCpf(cpf, nome, matricula));

        var page = jdbcTemplate.query(query.toString(), QueryMapper.rowMapperMatriculaNomeCpf);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), page.size());

        return new PageImpl<>(page.subList(start, end), pageable, page.size());
    }

    public QueryServidorRelatorioResponse buscaServidorPorCodigoServidorRelatorio(Integer codServidor) {

        StringBuilder query = new StringBuilder();

        query.append(queryRhModel.queryBuscaServidorRelatorio(codServidor));

        return jdbcTemplate.queryForObject(query.toString(), QueryMapper.rowMapperServidorRelatorio);
    }
}
