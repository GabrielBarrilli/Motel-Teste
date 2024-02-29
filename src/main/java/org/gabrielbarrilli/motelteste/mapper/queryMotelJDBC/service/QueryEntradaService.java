package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.service;

import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryEntrada;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.repository.QueryEntradaRepository;
import org.springframework.stereotype.Service;

@Service
public class QueryEntradaService {

    private final QueryEntradaRepository entradaRepository;

    public QueryEntradaService(QueryEntradaRepository entradaRepository) {
        this.entradaRepository = entradaRepository;
    }

    public void criarEntrada(QueryEntrada entrada) {

        entradaRepository.criarEntrada(entrada);
    }

}
