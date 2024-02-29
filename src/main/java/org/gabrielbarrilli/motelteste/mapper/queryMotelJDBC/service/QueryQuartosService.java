package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.service;

import org.gabrielbarrilli.motelteste.enums.StatusDoQuarto;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryQuartos;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.repository.QueryQuartosRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class QueryQuartosService {

    private final QueryQuartosRepository quartosRepository;

    public QueryQuartosService(QueryQuartosRepository quartosRepository) {
        this.quartosRepository = quartosRepository;
    }

    public void criarQuarto (QueryQuartos quartos, StatusDoQuarto statusDoQuarto) {

        quartosRepository.criarQuarto(quartos, statusDoQuarto);
    }

    public Page<QueryQuartos> obterQuartos (Pageable pageable) {

        Page<QueryQuartos> page = quartosRepository.obterQuartos(pageable);

        List<QueryQuartos> quartos = new ArrayList<>(page.getContent());

        quartos.sort(Comparator.comparing(QueryQuartos::id).reversed());

        return new PageImpl<>(quartos, pageable, page.getTotalElements());
    }

    public void atualizarQuarto(Long id, QueryQuartos quartos, StatusDoQuarto statusDoQuarto) {
        quartosRepository.atualizarQuarto(id, quartos, statusDoQuarto);
    }

    public void deletarQuarto(Long id) {
        quartosRepository.deletarQuarto(id);
    }
}
