package org.gabrielbarrilli.motelteste.response;

import org.gabrielbarrilli.motelteste.Enum.StatusEntrada;
import org.gabrielbarrilli.motelteste.Enum.StatusPagamento;
import org.gabrielbarrilli.motelteste.Enum.TipoPagamento;
import org.gabrielbarrilli.motelteste.model.Quartos;

import java.time.LocalDate;
import java.time.LocalTime;

public record EntradaResponse(
        Long id,
        String nomeLocador,
        LocalDate dataRegistroEntrada,
        LocalTime horaEntrada,
        StatusEntrada statusEntrada,
        TipoPagamento tipoPagamento,
        String placa,
        LocalDate dataSaida,
        LocalTime horaSaida,
        Long numero,
        StatusPagamento statusPagamento,
        Float totalEntrada,
        HorarioResponse horarioResponse
) {
}
