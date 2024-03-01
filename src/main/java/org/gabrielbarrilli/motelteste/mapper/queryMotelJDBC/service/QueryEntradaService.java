package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.service;

import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryEntrada;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.repository.QueryEntradaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class QueryEntradaService {

    private final QueryEntradaRepository entradaRepository;

    public QueryEntradaService(QueryEntradaRepository entradaRepository) {
        this.entradaRepository = entradaRepository;
    }

    public void criarEntrada(QueryEntrada entrada) {

        entradaRepository.criarEntrada(entrada);
    }

    public Page<QueryEntrada> obterEntradas (Pageable pageable) {

        Page<QueryEntrada> page = entradaRepository.obterEntradas(pageable);

        List<QueryEntrada> mapa = new ArrayList<>(page.getContent());

        mapa.sort(Comparator.comparing(QueryEntrada::id).reversed());

        return new PageImpl<>(mapa, pageable, page.getTotalElements());
    }

    public QueryEntrada obterEntradaPorId (Long id) {

        return entradaRepository.buscarPorId(id);
    }

    public void atualizarEntrada (Long id, QueryEntrada queryEntrada) {
        entradaRepository.atualizarEntrada(id, queryEntrada);
    }

    public void deletarEntrada(Long id) {

        entradaRepository.deletarEntrada(id);
    }
}
