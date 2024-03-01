package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model;

public record QueryEntradaConsumo(

        Long id,
        Float total,
        Long idItem,
        Integer quantidade,
        Long idEntrada
) {
}
