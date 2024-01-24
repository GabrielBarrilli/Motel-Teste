package org.gabrielbarrilli.motelteste.response;

import org.gabrielbarrilli.motelteste.model.Itens;

public record ConsumoResponse(
        Long id,
        Float total,
        String nomeItem,
        Integer Quantidade,
        Long idEntrada,
        String nomeLocador,
        Long numeroQuarto,
        Float totalEntrada
) {
}
