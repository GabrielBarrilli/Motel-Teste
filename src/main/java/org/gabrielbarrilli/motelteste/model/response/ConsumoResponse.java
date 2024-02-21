package org.gabrielbarrilli.motelteste.model.response;

public record ConsumoResponse(
        Long id,
        Float total,
        String nomeItem,
        Integer quantidade,
        Long idEntrada,
        String nomeLocador,
        Long numeroQuarto,
        Float totalEntrada
) {
}
