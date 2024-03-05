package org.gabrielbarrilli.motelteste.mapper.queryApiRh.controller;

import org.gabrielbarrilli.motelteste.mapper.queryApiRh.response.QueryCodigoServidorResponse;
import org.gabrielbarrilli.motelteste.mapper.queryApiRh.response.QueryMatriculaNomeCpfResponse;
import org.gabrielbarrilli.motelteste.mapper.queryApiRh.service.QueryRhService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/query")
public class QueryRhController {

    private final QueryRhService queryRhService;

    public QueryRhController(QueryRhService queryRhService) {
        this.queryRhService = queryRhService;
    }

    @ResponseStatus(OK)
    @GetMapping("/buscaServidorPorId/{id}")
    public QueryCodigoServidorResponse buscaServidorPorId(@PathVariable Integer id) {
        return queryRhService.buscaServidorPorId(id);
    }

    @ResponseStatus(OK)
    @GetMapping("buscaPorMatriculaNomeCpf/{cpf}/{nome}/{matricula}")
    public Page<QueryMatriculaNomeCpfResponse> buscaPorMatriculaNomeCpf(
            @RequestParam(required = false) @PathVariable String cpf,
            @RequestParam(required = false) @PathVariable String nome,
            @RequestParam(required = false) @PathVariable String matricula,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return queryRhService.buscaPorMatriculaNomeCpf(cpf, nome, matricula, pageable);
    }
}
