package org.gabrielbarrilli.motelteste.fixture;

import org.gabrielbarrilli.motelteste.model.request.CriarEntradaRequest;

public class CriarEntradaRequestFixture {

    public static CriarEntradaRequest criarEntradaRequest() {
        return new CriarEntradaRequest(
                "Locador teste 1",
                "Placa teste"
        );
    }
}
