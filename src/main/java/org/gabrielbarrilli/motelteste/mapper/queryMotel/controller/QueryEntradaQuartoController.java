package org.gabrielbarrilli.motelteste.mapper.queryMotel.controller;

import org.gabrielbarrilli.motelteste.mapper.queryMotel.response.QueryEntradaQuartoResponse;
import org.gabrielbarrilli.motelteste.mapper.queryMotel.service.QueryEntradaQuartoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/query")
public class QueryEntradaQuartoController {

    private final QueryEntradaQuartoService queryEntradaQuartoService;

    public QueryEntradaQuartoController(QueryEntradaQuartoService queryEntradaQuartoService) {
        this.queryEntradaQuartoService = queryEntradaQuartoService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/buscaEntradaQuartoPorId/{id}")
    public QueryEntradaQuartoResponse buscaEntradaQuartoPorId(@PathVariable Long id) {
        return queryEntradaQuartoService.buscaEntradaQuartoPorId(id);
    }
}
