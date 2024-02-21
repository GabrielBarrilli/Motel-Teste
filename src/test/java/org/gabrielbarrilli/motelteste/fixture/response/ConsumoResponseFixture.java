package org.gabrielbarrilli.motelteste.fixture.response;

import org.gabrielbarrilli.motelteste.fixture.EntradaFixture;
import org.gabrielbarrilli.motelteste.fixture.QuartosFixture;
import org.gabrielbarrilli.motelteste.model.response.ConsumoResponse;

import java.util.ArrayList;
import java.util.List;

public class ConsumoResponseFixture {

    public static ConsumoResponse consumoResponse1() {
        return new ConsumoResponse(
                1L,
                10F,
                "Água",
                1,
                EntradaFixture.entradaAtiva().getId(),
                "Locador Teste",
                QuartosFixture.quartoDisponivel().getId(),
                EntradaFixture.entradaAtiva().getTotalEntrada()
        );
    }

    public static ConsumoResponse consumoResponse2() {
        return new ConsumoResponse(
                2L,
                10F,
                "Água",
                1,
                EntradaFixture.entradaAtiva().getId(),
                "Locador Teste",
                QuartosFixture.quartoDisponivel().getId(),
                EntradaFixture.entradaAtiva().getTotalEntrada()
        );
    }

    public static List<ConsumoResponse> consumoResponseList() {

        List<ConsumoResponse> consumoResponseList = new ArrayList<>();

        consumoResponseList.add(consumoResponse1());
        consumoResponseList.add(consumoResponse2());

        return consumoResponseList;
    }
}
