package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model;

public record QueryMapaGeral(

        Long id,
        Integer apartamento,
        Float entrada,
        String report,
        Float saida,
        Float total,
        String hora,
        String data
) {
}
