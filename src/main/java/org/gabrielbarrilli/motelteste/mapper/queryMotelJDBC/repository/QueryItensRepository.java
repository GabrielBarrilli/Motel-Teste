package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryItens;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class QueryItensRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;

    public QueryItensRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    private final RowMapper<QueryItens> rowMapperQueryItens =
            ((rs, rowNum) -> new QueryItens(
                    rs.getLong("mt03_codigo_itens"),
                    rs.getString("mt03_descricao"),
                    rs.getFloat("mt03_valor")
            ));

    public void criarItem(QueryItens itens) {

        final var sql = """
                
                INSERT INTO mt03_itens (mt03_descricao, mt03_valor) VALUES (?, ?)""" ;

        jdbcTemplate.update(sql, itens.descricao(), itens.valor());
    }

    public Page<QueryItens> obterItens (int page, int size) {

        final var sql = """
                
                SELECT * FROM mt03_itens""";

        final var lista = jdbcTemplate.query(sql, rowMapperQueryItens);

        final var pageRequest = PageRequest.of(page, size);

        return new PageImpl<>(lista, pageRequest, size);
    }

    public void atualizarItem(QueryItens queryItens) {

        final var sql = """
                
                UPDATE mt03_itens SET mt03_descricao = ?, mt03_valor = ?
                WHERE mt03_codigo_itens = ?;
                """;

        jdbcTemplate.update(sql, queryItens.descricao(), queryItens.valor(), queryItens.id());
    }

    public void deletarItem (Long id) {

        final var sql = """
                
                DELETE FROM mt03_itens WHERE mt03_codigo_itens = ?""";

        jdbcTemplate.update(sql, id);
    }
}