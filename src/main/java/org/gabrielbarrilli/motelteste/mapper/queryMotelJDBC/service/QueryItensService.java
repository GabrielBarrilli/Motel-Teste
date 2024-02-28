package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.service;

import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryItens;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.repository.QueryItensRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class QueryItensService {

    private final QueryItensRepository queryItensRepository;

    public QueryItensService(QueryItensRepository queryItensRepository) {
        this.queryItensRepository = queryItensRepository;
    }

    public void criarItem(QueryItens queryItens) {
        queryItensRepository.criarItem(queryItens);
    }

    public Page<QueryItens> obterItens(Pageable pageable, int page, int size) {
        var retorno = queryItensRepository.obterItens(page, size)
                .stream()
                .sorted(Comparator.comparing(QueryItens::id).reversed())
                .toList();

        return new PageImpl<>(retorno, pageable, size);
    }

    public void atualizarItem(QueryItens queryItens) {
        queryItensRepository.atualizarItem(queryItens);
    }

    public void deletarItem(Long id) {
        queryItensRepository.deletarItem(id);
    }
}
