package org.gabrielbarrilli.motelteste.fixture;

import org.gabrielbarrilli.motelteste.model.request.ItensRequest;

public class ItensRequestFixture {

    public static ItensRequest itensRequest () {
        return new ItensRequest(
                "Água",
                10F
        );
    }
}
