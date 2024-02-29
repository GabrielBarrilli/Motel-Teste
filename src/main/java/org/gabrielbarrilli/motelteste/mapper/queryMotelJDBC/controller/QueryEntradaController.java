package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.controller;

import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryEntrada;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.service.QueryEntradaService;
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
}
