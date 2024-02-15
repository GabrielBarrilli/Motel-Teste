package org.gabrielbarrilli.motelteste.model.request;

public record EntradaRequest(
        Long idQuarto,
        String nomeLocador,
        String placa
) { }
