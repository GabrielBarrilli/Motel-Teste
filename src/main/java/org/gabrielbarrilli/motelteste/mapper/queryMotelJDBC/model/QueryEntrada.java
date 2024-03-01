package org.gabrielbarrilli.motelteste.mapper.queryMotelJDBC.model;

public record QueryEntrada(

        Long id,
        String nomeLocador,
        String dataEntrada,
        String horaEntrada,
        String statusEntrada,
        String tipoPagamento,
        String placa,
        String dataSaida,
        String horaSaida,
        Long numeroQuarto,
        String statusPagamento,
        Float totalEntrada
) {
}
