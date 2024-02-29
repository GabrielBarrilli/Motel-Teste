package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.service;

import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryMapaGeral;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryQuartos;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.repository.QueryMapaGeralRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class QueryMapaGeralService {

    private final QueryMapaGeralRepository mapaGeralRepository;

    public QueryMapaGeralService(QueryMapaGeralRepository mapaGeralRepository) {
        this.mapaGeralRepository = mapaGeralRepository;
    }

    public void criarMapa(QueryMapaGeral mapaGeral) {



        mapaGeralRepository.criarMapa(mapaGeral);
    }

    public Page<QueryMapaGeral> obterMapas (Pageable pageable) {

        Page<QueryMapaGeral> page = mapaGeralRepository.obterMapas(pageable);

        List<QueryMapaGeral> mapas = new ArrayList<>(page.getContent());

        mapas.sort(Comparator.comparing(QueryMapaGeral::id).reversed());

        return new PageImpl<>(mapas, pageable, page.getTotalElements());
    }

    public void atualizarMapa (Long id, QueryMapaGeral mapaGeral) {

        mapaGeralRepository.atualizarMapa(id, mapaGeral);
    }

    public void deletarMapa (Long id) {

        mapaGeralRepository.deletarMapa(id);
    }
}
