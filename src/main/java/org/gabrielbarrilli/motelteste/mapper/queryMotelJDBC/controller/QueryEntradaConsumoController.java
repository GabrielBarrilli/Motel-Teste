package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.controller;

import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model.QueryEntradaConsumo;
import org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.service.QueryEntradaConsumoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("consumoQuery")
public class QueryEntradaConsumoController {

    private final QueryEntradaConsumoService entradaConsumoService;

    public QueryEntradaConsumoController(QueryEntradaConsumoService entradaConsumoService) {
        this.entradaConsumoService = entradaConsumoService;
    }

    @ResponseStatus(CREATED)
    @PostMapping("/criarConsumo/{idItem}/{idEntrada}")
    public void criarEntradaConsumo(QueryEntradaConsumo entradaConsumo, @PathVariable Long idItem, @PathVariable Long idEntrada) {
        entradaConsumoService.criarEntradaConsumo(entradaConsumo, idItem, idEntrada);
    }

    @ResponseStatus(OK)
    @GetMapping("/obterConsumos")
    public Page<QueryEntradaConsumo> obterEntradasConsumo (Pageable pageable) {

        return entradaConsumoService.obterEntradasConsumo(pageable);
    }

    @ResponseStatus(OK)
    @GetMapping("/obterConsumosPorId")
    public Page<QueryEntradaConsumo> obterConsumosPorId (Long id, Pageable pageable) {
        return entradaConsumoService.obterConsumosPorId(id, pageable);
    }

    @ResponseStatus(OK)
    @PutMapping("/atualizarConsumo")
    public void atualizarConsumo (Long id, QueryEntradaConsumo entradaConsumo) {
        entradaConsumoService.atualizarConsumo(id, entradaConsumo);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/deletarConsumo")
    public void deleteConsumo (Long id) {
        entradaConsumoService.deleteConsumo(id);
    }
}

