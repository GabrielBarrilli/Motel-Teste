package org.gabrielbarrilli.motelteste.fixture;

import org.gabrielbarrilli.motelteste.model.request.EntradaRequest;

public class EntradaRequestFixture {

    public static EntradaRequest entradaRequestQuarto1() {
        return new EntradaRequest(
                1L,
                "Locador teste 1",
                "Placa teste"
        );
    }

    public static EntradaRequest entradaRequestQuarto2() {
        return new EntradaRequest(
                2L,
                "Locador teste 1",
                "Placa teste"
        );
    }
}
