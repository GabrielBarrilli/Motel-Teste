package org.gabrielbarrilli.motelteste.fixture.request;

import org.gabrielbarrilli.motelteste.model.request.ConsumoRequest;
import org.gabrielbarrilli.motelteste.model.request.CriarEntradaRequest;

public class CriarConsumoRequestFixture {

    public static ConsumoRequest criarConsumoRequestFixture() {
        return new ConsumoRequest(
                1
        );
    }
}
