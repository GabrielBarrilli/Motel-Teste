package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model;

import org.gabrielbarrilli.motelteste.enums.StatusDoQuarto;

public record QueryQuartos (
        Long id,
        Long numeroQuarto,
        String descricao,
        Integer capacidadePessoa,
        String statusDoQuarto
) {
}
