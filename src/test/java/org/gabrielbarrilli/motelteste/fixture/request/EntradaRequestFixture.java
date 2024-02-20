package org.gabrielbarrilli.motelteste.fixture.request;

import org.gabrielbarrilli.motelteste.model.request.CriarEntradaRequest;

public class EntradaRequestFixture {

    public static CriarEntradaRequest entradaRequestQuarto1() {
        return new CriarEntradaRequest(
                1L,
                "Locador teste 1",
                "Placa teste"
        );
    }

    public static CriarEntradaRequest entradaRequestQuarto2() {
        return new CriarEntradaRequest(
                2L,
                "Locador teste 1",
                "Placa teste"
        );
    }
}
