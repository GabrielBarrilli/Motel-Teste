package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.controller;

import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryEntrada;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.service.QueryEntradaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("entradaQuery")
public class QueryEntradaController {

    private final QueryEntradaService entradaService;

    public QueryEntradaController(QueryEntradaService entradaService) {
        this.entradaService = entradaService;
    }

    @ResponseStatus(CREATED)
    @PostMapping("/criarEntradaQuery")
    public void criarEntrada(QueryEntrada entrada) {

        entradaService.criarEntrada(entrada);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/obterEntradas")
    public Page<QueryEntrada> obterEntradas(Pageable pageable) {
        return entradaService.obterEntradas(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/obterEntradasPorId")
    public QueryEntrada obterEntradaPorId(Long id) {
        return entradaService.obterEntradaPorId(id);
    }

    @PutMapping("/atualizarEntrada")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarEntrada (Long id, QueryEntrada queryEntrada) {
        entradaService.atualizarEntrada(id, queryEntrada);
    }

}
