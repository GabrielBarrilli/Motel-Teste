package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.service;

import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryEntradaConsumo;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.repository.QueryEntradaConsumoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class QueryEntradaConsumoService {

    private final QueryEntradaConsumoRepository entradaConsumoRepository;

    public QueryEntradaConsumoService(QueryEntradaConsumoRepository entradaConsumoRepository) {
        this.entradaConsumoRepository = entradaConsumoRepository;
    }

    public void criarEntradaConsumo(QueryEntradaConsumo entradaConsumo, Long idItem, Long idEntrada) {
        entradaConsumoRepository.criarEntradaConsumo(entradaConsumo, idItem, idEntrada);
    }

    public Page<QueryEntradaConsumo> obterEntradasConsumo (Pageable pageable) {

        Page<QueryEntradaConsumo> page = entradaConsumoRepository.obterEntradasConsumo(pageable);

        List<QueryEntradaConsumo> mapas = new ArrayList<>(page.getContent());

        mapas.sort(Comparator.comparing(QueryEntradaConsumo::id).reversed());

        return new PageImpl<>(mapas, pageable, page.getTotalElements());
    }

    public Page<QueryEntradaConsumo> obterConsumosPorId (Long id, Pageable pageable) {

        Page<QueryEntradaConsumo> page = entradaConsumoRepository.obterConsumosPorIdEntrada(id, pageable);

        List<QueryEntradaConsumo> mapas = new ArrayList<>(page.getContent());

        mapas.sort(Comparator.comparing(QueryEntradaConsumo::id).reversed());

        return new PageImpl<>(mapas, pageable, page.getTotalElements());
    }

    public void atualizarConsumo (Long id, QueryEntradaConsumo entradaConsumo) {
        entradaConsumoRepository.atualizarConsumo(id, entradaConsumo);
    }

    public void deleteConsumo (Long id) {
        entradaConsumoRepository.deleteConsumo(id);
    }
}
