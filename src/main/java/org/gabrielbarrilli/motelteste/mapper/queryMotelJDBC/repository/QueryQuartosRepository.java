package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryQuartos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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

    public void criarQuarto(QueryQuartos quartos) {

        final var sql = """
                
                INSERT INTO mt02_quartos (mt02_descricao, mt02_capacidade_pessoa, mt02_status_do_quarto) VALUES(?, ?, ?)""";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"mt02_codigo_quartos"});
            ps.setString(1, quartos.descricao());
            ps.setInt(2, quartos.capacidadePessoa());
            ps.setString(3, quartos.statusDoQuarto());
            return ps;
        }, keyHolder);

        // Obtém o ID gerado automaticamente
        long idGerado = requireNonNull(keyHolder.getKey()).longValue();

        // Agora você pode usar o ID gerado para criar a numeração do quarto
        String numeroDoQuarto = "Q" + idGerado;

        // Atualiza o quarto com a numeração
        atualizarNumeroDoQuarto(idGerado, numeroDoQuarto);
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

    private void atualizarNumeroDoQuarto(long idQuarto, String numeroDoQuarto) {
        String numeroSemQ = numeroDoQuarto.replace("Q", "");

        final var sql = "UPDATE mt02_quartos SET mt02_numero = CAST(? AS BIGINT) WHERE mt02_codigo_quartos = ?";
        jdbcTemplate.update(sql, numeroSemQ, idQuarto);
    }
}
