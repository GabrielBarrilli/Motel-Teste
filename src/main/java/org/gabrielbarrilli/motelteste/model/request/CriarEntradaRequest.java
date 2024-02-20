package org.gabrielbarrilli.motelteste.model.request;

public record CriarEntradaRequest(
        Long idQuarto,
        String nomeLocador,
        String placa
) {
}
