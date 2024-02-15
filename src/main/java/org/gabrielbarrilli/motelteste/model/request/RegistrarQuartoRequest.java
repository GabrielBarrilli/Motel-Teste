package org.gabrielbarrilli.motelteste.model.request;

public record RegistrarQuartoRequest(
        String descricao,
        Integer capacidadePessoa
) {
}
