package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.service;

import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryQuartos;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.repository.QueryQuartosRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class QueryQuartosService {

    private final QueryQuartosRepository quartosRepository;

    public QueryQuartosService(QueryQuartosRepository quartosRepository) {
        this.quartosRepository = quartosRepository;
    }

    public void criarQuarto (QueryQuartos quartos) {

        quartosRepository.criarQuarto(quartos);
    }

    public Page<QueryQuartos> obterQuartos(Pageable pageable, int page, int size) {
        var retorno = quartosRepository.obterQuartos(page, size)
                .stream()
                .sorted(Comparator.comparing(QueryQuartos::id).reversed())
                .toList();

        return new PageImpl<>(retorno, pageable, size);
    }

    public void atualizarQuarto(QueryQuartos quartos) {
        quartosRepository.atualizarQuarto(quartos);
    }

    public void deletarQuarto(Long id) {
        quartosRepository.deletarQuarto(id);
    }
}
