package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryEntrada;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryEntradaConsumo;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryItens;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class QueryEntradaConsumoRepository {

    private final JdbcTemplate jdbcTemplate;

    private final ObjectMapper objectMapper;

    private final QueryEntradaRepository entradaRepository;

    private final QueryItensRepository itemRepository;

    public QueryEntradaConsumoRepository(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper, QueryEntradaRepository entradaRepository, QueryItensRepository itemRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.objectMapper = objectMapper;
        this.entradaRepository = entradaRepository;
        this.itemRepository = itemRepository;
    }

    private static final RowMapper<QueryEntradaConsumo> rowMapperEntradaConsumo =
            ((rs, rowNum) -> new QueryEntradaConsumo(
                    rs.getLong("mt03_codigo_entrada_produto"),
                    rs.getFloat("mt03_total"),
                    rs.getLong("fkmt03mt04_codigo_itens"),
                    rs.getInt("mt03_quantidade"),
                    rs.getLong("fkmt03mt01_codigo_entrada")
            ));

    public void criarEntradaConsumo (QueryEntradaConsumo queryEntradaConsumo, Long idItem, Long idEntrada) {

        final var sql = """
                
                INSERT INTO mt03_entrada_consumo
                (mt03_total, fkmt03mt04_codigo_itens, mt03_quantidade, fkmt03mt01_codigo_entrada)
                VALUES
                (?,?,?,?)""";

        var item = itemRepository.obterItemPorId(idItem);
        var entrada = entradaRepository.buscarPorId(idEntrada);

        var total = item.valor() * queryEntradaConsumo.quantidade();

        if (entrada == null) {
            throw new EntityNotFoundException("Entrada inexistente!");
        }

        if (item == null) {
            throw new EntityNotFoundException("Item inexistente!");
        }

        jdbcTemplate.update(sql,
                total,
                item.id(),
                queryEntradaConsumo.quantidade(),
                entrada.id()
        );
    }

    public Page<QueryEntradaConsumo> obterEntradasConsumo (Pageable pageable) {

        final var sql = """
                
                SELECT * FROM mt03_entrada_consumo""";

        final var lista = jdbcTemplate.query(sql, rowMapperEntradaConsumo);

        int start = (int) pageable.getOffset();

        int end = Math.min((start + pageable.getPageSize()), lista.size());

        return new PageImpl<>(lista.subList(start, end), pageable, lista.size());
    }

    public Page<QueryEntradaConsumo> obterConsumosPorIdEntrada (Long id, Pageable pageable) {

        final var sql = """
                
                SELECT * FROM mt03_entrada_consumo
                WHERE fkmt03mt01_codigo_entrada =?""";

        final var lista = jdbcTemplate.query(sql, rowMapperEntradaConsumo, id);

        int start = (int) pageable.getOffset();

        int end = Math.min((start + pageable.getPageSize()), lista.size());

        return new PageImpl<>(lista.subList(start, end), pageable, lista.size());
    }

    public void atualizarConsumo (Long id, QueryEntradaConsumo entradaConsumo) {

        final var sql = """
                
                UPDATE mt03_entrada_consumo
                SET
                    mt03_total = ?,
                    fkmt03mt04_codigo_itens = ?,
                    mt03_quantidade = ?,
                    fkmt03mt01_codigo_entrada =?
                WHERE mt03_codigo_entrada_produto = ?""";

        var entrada = entradaRepository.buscarPorId(entradaConsumo.idEntrada());
        var item = itemRepository.obterItemPorId(entradaConsumo.idItem());

        var total = item.valor() * entradaConsumo.quantidade();

        if (entrada == null) {
            throw new EntityNotFoundException("Entrada inexistente!");
        }

        if (item == null) {
            throw new EntityNotFoundException("Item inexistente!");
        }

        jdbcTemplate.update(sql,
                total,
                item.id(),
                entradaConsumo.quantidade(),
                entrada.id(),
                id);
    }

    public void deleteConsumo (Long id) {

        final var sql = """
                
                DELETE FROM mt03_entrada_consumo
                WHERE mt03_codigo_entrada_produto =?""";

        jdbcTemplate.update(sql, id);
    }
}
