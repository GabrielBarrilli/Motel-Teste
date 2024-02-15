package org.gabrielbarrilli.motelteste.fixture;

import org.gabrielbarrilli.motelteste.model.request.EntradaRequest;

public class EntradaRequestFixture {

    public static EntradaRequest entradaRequest() {
        return new EntradaRequest(
                1L,
                "Locador teste 1",
                "Placa teste 1"
        );
    }
}
