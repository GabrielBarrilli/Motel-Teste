package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.controller;

import org.gabrielbarrilli.motelteste.enums.StatusDoQuarto;
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
    public void criarQuarto(QueryQuartos quartos, StatusDoQuarto statusDoQuarto) {
        quartosService.criarQuarto(quartos, statusDoQuarto);
    }

    @ResponseStatus(OK)
    @GetMapping("/obterQuartosQuery")
    public Page<QueryQuartos> obterQuartos (Pageable pageable) {
        return quartosService.obterQuartos(pageable);
    }

    @ResponseStatus(OK)
    @GetMapping("/obterQuartoByIdQuery/{id}")
    public QueryQuartos busqueById (@PathVariable Long id) {
        return quartosService.busqueById(id);
    }

    @ResponseStatus(OK)
    @PutMapping("/atualizarQuartoQuery")
    public void atualizarQuarto(Long id, QueryQuartos quartos, StatusDoQuarto statusDoQuarto) {

        quartosService.atualizarQuarto(id, quartos, statusDoQuarto);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/deletarQuartoQuery/{id}")
    public void deletarQuarto(@PathVariable Long id) {

        quartosService.deletarQuarto(id);
    }
}
