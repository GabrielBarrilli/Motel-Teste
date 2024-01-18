package org.gabrielbarrilli.motelteste.request;

public record RegistrarQuartoRequest(
        String descricao,
        Integer capacidadePessoa
) {
}
