package org.gabrielbarrilli.motelteste.mapper.queryApiRh.controller;

import org.gabrielbarrilli.motelteste.mapper.queryApiRh.response.QueryCodigoServidorResponse;
import org.gabrielbarrilli.motelteste.mapper.queryApiRh.response.QueryMatriculaNomeCpfResponse;
import org.gabrielbarrilli.motelteste.mapper.queryApiRh.service.QueryRhService;
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
    public QueryCodigoServidorResponse buscaServidorPorId (@PathVariable Integer id) {
        return queryRhService.buscaServidorPorId(id);
    }

    @ResponseStatus(OK)
    @GetMapping("buscaPorMatriculaNomeCpf/{cpf}/{nome}/{matricula}")
    public QueryMatriculaNomeCpfResponse buscaPorMatriculaNomeCpf(@PathVariable String cpf, @PathVariable String nome, @PathVariable String matricula) {
        return queryRhService.buscaPorMatriculaNomeCpf(cpf, nome, matricula);
    }
}
