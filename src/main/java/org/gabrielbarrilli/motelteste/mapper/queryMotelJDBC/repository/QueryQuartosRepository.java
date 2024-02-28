package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryQuartos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class QueryQuartosRepository {

    private final JdbcTemplate jdbcTemplate;

    private final ObjectMapper objectMapper;

    public QueryQuartosRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    private final RowMapper<QueryQuartos> rowMapperQueryQuartos =
            ((rs, rowNum) -> new QueryQuartos(
                    rs.getLong("mt02_codigo_quartos"),
                    rs.getLong("mt02_numero"),
                    rs.getString("mt02_descricao"),
                    rs.getInt("mt02_capacidade_pessoa"),
                    rs.getString("mt02_status_do_quarto")
            ));

    public void criarQuarto(QueryQuartos quartos) {

        final var sql = """
                
                INSERT INTO mt02_quartos (mt02_descricao, mt02_capacidade_pessoa, mt02_status_do_quarto) VALUES(?, ?, ?)""";

        jdbcTemplate.update(sql, quartos.descricao(), quartos.capacidadePessoa(), quartos.statusDoQuarto());
    }

    public Page<QueryQuartos> obterQuartos (int page, int size) {

        final var sql = """
                
                SELECT * FROM mt02_quartos""";

        final var lista = jdbcTemplate.query(sql, rowMapperQueryQuartos);

        final var pageRequest = PageRequest.of(page, size);

        return new PageImpl<>(lista, pageRequest, size);
    }

    public void atualizarQuarto(QueryQuartos quartos) {

        final var sql = """
                
                UPDATE mt02_quartos SET mt02_numero = ?,
                mt02_descricao = ?,
                mt02_capacidade_pessoa = ?,
                mt02_status_do_quarto = ?
                WHERE mt02_codigo_quartos = ?;
                """;

        jdbcTemplate.update(sql, quartos.id(), quartos.descricao(),
                quartos.capacidadePessoa(), quartos.statusDoQuarto(), quartos.id());
    }

    public void deletarQuarto (Long id) {

        final var sql = """
                
                DELETE FROM mt02_quartos WHERE mt02_codigo_quartos = ?""";

        jdbcTemplate.update(sql, id);
    }
}
