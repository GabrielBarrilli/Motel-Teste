package org.gabrielbarrilli.motelteste.request;

import org.gabrielbarrilli.motelteste.Enum.StatusEntrada;
import org.gabrielbarrilli.motelteste.Enum.StatusPagamento;
import org.gabrielbarrilli.motelteste.Enum.TipoPagamento;

public record EntradaRequest(
        Long idQuarto,
        String nomeLocador,
        String placa
) { }
