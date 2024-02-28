package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.controller;

import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryItens;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.service.QueryItensService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/itensQuery")
public class QueryItensController {

    private final QueryItensService queryItensService;

    public QueryItensController(QueryItensService queryItensService) {
        this.queryItensService = queryItensService;
    }

    @ResponseStatus(CREATED)
    @PostMapping("/criarItemQuery")
    public void criarItem(QueryItens queryItens) {
        queryItensService.criarItem(queryItens);
    }

    @ResponseStatus(OK)
    @GetMapping("/obterItensQuery")
    public Page<QueryItens> obterItens(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return queryItensService.obterItens(pageable, page, size);
    }

    @ResponseStatus(OK)
    @PutMapping("/atualizarItemQuery")
    public void atualizarItem(QueryItens queryItens) {
        queryItensService.atualizarItem(queryItens);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/deletarItemQuery/{id}")
    public void deletarItem (@PathVariable Long id) {
        queryItensService.deletarItem(id);
    }
}
