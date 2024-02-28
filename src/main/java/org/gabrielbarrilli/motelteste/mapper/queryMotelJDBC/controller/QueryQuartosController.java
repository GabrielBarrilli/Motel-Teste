package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.controller;

import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryItens;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryQuartos;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.service.QueryQuartosService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/quartosQuery")
public class QueryQuartosController {

    private final QueryQuartosService quartosService;

    public QueryQuartosController(QueryQuartosService quartosService) {
        this.quartosService = quartosService;
    }

    @ResponseStatus(CREATED)
    @PostMapping("/criarQuartoQuery")
    public void criarQuarto(QueryQuartos quartos) {
        quartosService.criarQuarto(quartos);
    }

    @ResponseStatus(OK)
    @GetMapping("/obterQuartosQuery")
    public Page<QueryQuartos> obterQuartos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return quartosService.obterQuartos(pageable, page, size);
    }

    @ResponseStatus(OK)
    @PutMapping("/atualizarQuartoQuery")
    public void atualizarQuarto(QueryQuartos quartos) {

        quartosService.atualizarQuarto(quartos);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/deletarQuartoQuery/{id}")
    public void deletarQuarto(@PathVariable Long id) {

        quartosService.deletarQuarto(id);
    }
}
