package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gabrielbarrilli.motelteste.enums.StatusDoQuarto;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryQuartos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

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

    public void criarQuarto(QueryQuartos quartos, StatusDoQuarto statusDoQuarto) {

        final var sql = """
                
                INSERT INTO mt02_quartos (mt02_descricao, mt02_capacidade_pessoa, mt02_status_do_quarto) VALUES(?, ?, ?)""";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"mt02_codigo_quartos"});
            ps.setString(1, quartos.descricao());
            ps.setInt(2, quartos.capacidadePessoa());
            ps.setString(3, statusDoQuarto.toString());
            return ps;
        }, keyHolder);

        long idGerado = requireNonNull(keyHolder.getKey()).longValue();

        String numeroDoQuarto = "Q" + idGerado;

        atualizarNumeroDoQuarto(idGerado, numeroDoQuarto);
    }


    public Page<QueryQuartos> obterQuartos (Pageable pageable) {

        final var sql = """
                
                SELECT * FROM mt02_quartos""";

        final var lista = jdbcTemplate.query(sql, rowMapperQueryQuartos);

        int start = (int) pageable.getOffset();

        int end = Math.min((start + pageable.getPageSize()), lista.size());

        return new PageImpl<>(lista.subList(start, end), pageable, lista.size());
    }

    public void atualizarQuarto(Long id, QueryQuartos quartos, StatusDoQuarto statusDoQuarto) {

        final var sql = """
                
                UPDATE mt02_quartos SET mt02_numero = ?,
                mt02_descricao = ?,
                mt02_capacidade_pessoa = ?,
                mt02_status_do_quarto = ?
                WHERE mt02_codigo_quartos = ?;
                """;

        jdbcTemplate.update(sql, id, quartos.descricao(),
                quartos.capacidadePessoa(), statusDoQuarto.toString(), id);
    }

    public void deletarQuarto (Long id) {

        final var sql = """
                
                DELETE FROM mt02_quartos WHERE mt02_codigo_quartos = ?""";

        jdbcTemplate.update(sql, id);
    }

    private void atualizarNumeroDoQuarto(long idQuarto, String numeroDoQuarto) {
        String numeroSemQ = numeroDoQuarto.replace("Q", "");

        final var sql = "UPDATE mt02_quartos SET mt02_numero = CAST(? AS BIGINT) WHERE mt02_codigo_quartos = ?";
        jdbcTemplate.update(sql, numeroSemQ, idQuarto);
    }
}
