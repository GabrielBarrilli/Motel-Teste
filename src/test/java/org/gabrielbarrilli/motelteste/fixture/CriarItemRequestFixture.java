package org.gabrielbarrilli.motelteste.fixture;

import org.gabrielbarrilli.motelteste.model.request.CriarEntradaRequest;
import org.gabrielbarrilli.motelteste.model.request.ItensRequest;

public class CriarItemRequestFixture {

    public static ItensRequest criarItemRequestFixture() {
        return new ItensRequest(
                "Locador teste 1",
                10F
        );
    }
}
