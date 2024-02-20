package org.gabrielbarrilli.motelteste.fixture.request;

import org.gabrielbarrilli.motelteste.model.request.CriarEntradaRequest;

public class CriarEntradaRequestFixture {

    public static CriarEntradaRequest criarEntradaRequest() {
        return new CriarEntradaRequest(
                1L,
                "Locador teste 1",
                "Placa teste"
        );
    }
}
