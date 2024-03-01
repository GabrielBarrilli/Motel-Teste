package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.gabrielbarrilli.motelteste.enums.StatusPagamento;
import org.gabrielbarrilli.motelteste.enums.TipoPagamento;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryEntrada;
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

import static java.time.LocalTime.parse;
import static org.gabrielbarrilli.motelteste.enums.StatusEntrada.ATIVA;

@Repository
public class QueryEntradaRepository {

    private final JdbcTemplate jdbcTemplate;

    private final ObjectMapper objectMapper;

    private final QueryQuartosRepository quartosRepository;

    public QueryEntradaRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper, QueryQuartosRepository quartosRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
        this.quartosRepository = quartosRepository;
    }

    private final RowMapper<QueryEntrada> rowMapperQueryEntrada =
            ((rs, rowNum) -> new QueryEntrada(
                    rs.getLong("mt01_codigo_entrada"),
                    rs.getString("mt01_nome_locador"),
                    rs.getString("mt01_data_registro_entrada"),
                    rs.getString("mt01_hora_entrada"),
                    rs.getString("mt01_status_entrada"),
                    rs.getString("mt01_tipo_pagamento"),
                    rs.getString("mt01_placa"),
                    rs.getString("mt01_data_saida"),
                    rs.getString("mt01_hora_saida"),
                    rs.getLong("fkmt01mt02_codigo_quartos"),
                    rs.getString("mt01_status_pagamento"),
                    rs.getFloat("mt01_total_entrada")
            ));

    public void criarEntrada(QueryEntrada entrada) {

        final var sql = """
                INSERT INTO mt01_entrada
                (mt01_nome_locador,
                mt01_data_registro_entrada,
                mt01_hora_entrada,
                mt01_status_entrada,
                mt01_tipo_pagamento,
                mt01_placa,
                mt01_data_saida,
                mt01_hora_saida,
                fkmt01mt02_codigo_quartos,
                mt01_status_pagamento,
                mt01_total_entrada)
                VALUES
                (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        var quarto = quartosRepository.busqueById(entrada.numeroQuarto());

        var data = LocalDate.now().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaFormatada = LocalTime.now().format(formatter);
        Time time = Time.valueOf(horaFormatada);

        var statusEntrada = ATIVA.toString();
        var tipoPagamento = TipoPagamento.PENDENTE.toString();
        var statusPagamento = StatusPagamento.PENDENTE.toString();

        if (quarto == null) {
            throw new EntityNotFoundException("Quarto inexistente!");
        }

        jdbcTemplate.update(sql,
                entrada.nomeLocador(),
                Date.valueOf(data),
                time,
                statusEntrada,
                tipoPagamento,
                entrada.placa(),
                null,
                null,
                entrada.numeroQuarto(),
                statusPagamento,
                0
        );

    }

    public Page<QueryEntrada> obterEntradas (Pageable pageable) {

        final var sql = """
                
                SELECT * FROM mt01_entrada""";

        final var lista = jdbcTemplate.query(sql, rowMapperQueryEntrada);

        int start = (int) pageable.getOffset();

        int end = Math.min((start + pageable.getPageSize()), lista.size());

        return new PageImpl<>(lista.subList(start, end), pageable, lista.size());
    }

    public QueryEntrada buscarPorId (Long id) {

        final var sql = """
                
                SELECT * FROM mt01_entrada
                WHERE mt01_codigo_entrada = ?
                
                """;

        return jdbcTemplate.queryForObject(sql, rowMapperQueryEntrada, id);
    }

    public void atualizarEntrada (Long id, QueryEntrada entrada) {

        final var sql = """
                
                UPDATE mt01_entrada
                SET
                    mt01_status_entrada = ?,
                    mt01_tipo_pagamento = ?,
                    mt01_placa = ?,
                    mt01_hora_saida = ?,
                    fkmt01mt02_codigo_quartos = ?,
                    mt01_status_pagamento = ?
                WHERE mt01_codigo_entrada = ?
                """;

        LocalTime horaSaida = parse(entrada.horaSaida());

        jdbcTemplate.update(sql,
                entrada.statusEntrada(),
                entrada.tipoPagamento(),
                entrada.placa(),
                horaSaida,
                entrada.numeroQuarto(),
                entrada.statusPagamento(),
                id
        );
    }

    public void deletarEntrada(Long id) {

        final var sql = """
                
                DELETE FROM mt01_entrada WHERE mt01_codigo_entrada = ?""";

        jdbcTemplate.update(sql, id);
    }

}
