package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.controller;

import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryMapaGeral;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.service.QueryMapaGeralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/mapasQuery")
public class QueryMapaGeralController {

    private final QueryMapaGeralService mapaGeralService;

    public QueryMapaGeralController(QueryMapaGeralService mapaGeralService) {
        this.mapaGeralService = mapaGeralService;
    }
    @ResponseStatus(CREATED)
    @PostMapping("/criarMapaGeralQuery")
    public void criarMapa(QueryMapaGeral mapaGeral) {

        mapaGeralService.criarMapa(mapaGeral);
    }

    @ResponseStatus(OK)
    @GetMapping("/obterMapasQuery")
    public Page<QueryMapaGeral> obterMapas(Pageable pageable) {
        return mapaGeralService.obterMapas(pageable);
    }

    @ResponseStatus(OK)
    @PutMapping("/atualizarMapaGeralQuery/{id}")
    public void atualizarMapa(@PathVariable Long id, QueryMapaGeral mapaGeral) {

        mapaGeralService.atualizarMapa(id, mapaGeral);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/deletarMapaGeralQuery/{id}")
    public void deletarMapa(@PathVariable Long id) {

        mapaGeralService.deletarMapa(id);
    }
}