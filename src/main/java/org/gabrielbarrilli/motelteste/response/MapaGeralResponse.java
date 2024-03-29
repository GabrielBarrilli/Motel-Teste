package org.gabrielbarrilli.motelteste.response;

import org.gabrielbarrilli.motelteste.model.Entrada;

import java.time.LocalDate;
import java.time.LocalTime;

public record MapaGeralResponse(
        Long id,
        Float entrada,
        String report,
        Float saida,
        Float totalCaixa,
        LocalTime hora,
        LocalDate data
) {
}
