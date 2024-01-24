package org.gabrielbarrilli.motelteste.request;

public record EntradaRequest(
        Long idQuarto,
        String nomeLocador,
        String placa
) { }
