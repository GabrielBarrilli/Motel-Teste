package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryMapaGeral;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Repository
public class QueryMapaGeralRepository {

    private final JdbcTemplate jdbcTemplate;

    private final ObjectMapper objectMapper;

    public QueryMapaGeralRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
    }

    private final RowMapper<QueryMapaGeral> rowMapperQueryMapaGeral =
            ((rs, rowNum) -> new QueryMapaGeral(
                    rs.getLong("mt05_codigo_mapa_geral"),
                    rs.getInt("mt05_apartamento"),
                    rs.getFloat("mt05_entrada"),
                    rs.getString("mt05_report"),
                    rs.getFloat("mt05_saida"),
                    rs.getFloat("mt05_total"),
                    rs.getString("mt05_hora"),
                    rs.getString("mt05_data")
            ));

    public void criarMapa(QueryMapaGeral mapaGeral) {

        final var sql = """
                
                INSERT INTO mt05_mapa_geral (
                mt05_apartamento, mt05_entrada, mt05_report, mt05_saida, mt05_total, mt05_hora, mt05_data)
                VALUES
                (?, ?, ?, ?, ?, ?, ?)""";

        var data = LocalDate.now().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaFormatada = LocalTime.now().format(formatter);
        Time time = Time.valueOf(horaFormatada);

        var total = mapaGeral.entrada() - mapaGeral.saida();

        jdbcTemplate.update(sql, mapaGeral.apartamento(), mapaGeral.entrada(), mapaGeral.report(), mapaGeral.saida(), total, time, Date.valueOf(data));
    }

    public Page<QueryMapaGeral> obterMapas (Pageable pageable) {

        final var sql = """
                
                SELECT * FROM mt05_mapa_geral""";

        final var lista = jdbcTemplate.query(sql, rowMapperQueryMapaGeral);

        int start = (int) pageable.getOffset();

        int end = Math.min((start + pageable.getPageSize()), lista.size());

        return new PageImpl<>(lista.subList(start, end), pageable, lista.size());
    }

    public void atualizarMapa (Long id, QueryMapaGeral mapaGeral) {

        final var sql = """
                
                UPDATE mt05_mapa_geral SET
                    mt05_apartamento = ?,
                    mt05_entrada = ?,
                    mt05_report = ?,
                    mt05_saida = ?,
                    mt05_total = ?,
                    mt05_hora = ?,
                    mt05_data = ?
                WHERE mt05_codigo_mapa_geral = ?
                """;

        jdbcTemplate.update(sql, mapaGeral.apartamento(),
                mapaGeral.entrada(), mapaGeral.report(),
                mapaGeral.saida(), mapaGeral.total(),
                mapaGeral.hora(), mapaGeral.data(), id);
    }

    public void deletarMapa (Long id) {

        final var sql = """
                
                DELETE FROM mt05_mapa_geral WHERE mt05_codigo_mapa_geral = ?""";

        jdbcTemplate.update(sql, id);
    }
}
