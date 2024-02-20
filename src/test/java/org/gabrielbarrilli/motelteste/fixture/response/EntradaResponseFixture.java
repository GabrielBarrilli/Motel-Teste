package org.gabrielbarrilli.motelteste.fixture.response;

import org.gabrielbarrilli.motelteste.enums.StatusEntrada;
import org.gabrielbarrilli.motelteste.enums.StatusPagamento;
import org.gabrielbarrilli.motelteste.enums.TipoPagamento;
import org.gabrielbarrilli.motelteste.fixture.QuartosFixture;
import org.gabrielbarrilli.motelteste.model.response.EntradaResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EntradaResponseFixture {

    public static EntradaResponse entradaResponseAtiva() {
        return new EntradaResponse(
                1L,
                "Locador teste 1",
                LocalDate.now().minusDays(1),
                LocalTime.of(10,10,10),
                StatusEntrada.ATIVA,
                TipoPagamento.PENDENTE,
                "Placa teste 1",
                null,
                null,
                QuartosFixture.quartoDisponivel().getNumero(),
                StatusPagamento.PENDENTE,
                100F
        );
    }

    public static EntradaResponse entradaResponseFinalizada() {
        return new EntradaResponse(
                4L,
                "Gabriel",
                LocalDate.now().minusDays(1),
                LocalTime.of(10,10,10),
                StatusEntrada.FINALIZADA,
                TipoPagamento.PENDENTE,
                "Placa teste",
                LocalDate.now(),
                LocalTime.of(10,10,10),
                QuartosFixture.quartoDisponivel().getNumero(),
                StatusPagamento.PENDENTE,
                0F
        );
    }

    public static List<EntradaResponse> entradaResponseList() {
        List<EntradaResponse> entradaResponse = new ArrayList<>();

        entradaResponse.add(entradaResponseAtiva());
        entradaResponse.add(entradaResponseFinalizada());

        return entradaResponse;
    }
}
