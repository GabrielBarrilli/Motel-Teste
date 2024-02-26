package org.gabrielbarrilli.motelteste.mapper.queryMotel.response;

public record QueryEntradaQuartoResponse(
        Long idEntrada,
        String nomeLocador,
        String placa,
        QuartoResponse quartoResponse
) {
    public record QuartoResponse(
            Long numeroQuarto,
            String descricaoQuarto,
            Integer capacidadePessoa
    ) {}
}
