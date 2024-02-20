package org.gabrielbarrilli.motelteste.fixture;

import org.gabrielbarrilli.motelteste.model.request.RegistrarQuartoRequest;

public class CreateQuartoRequest {

    public static RegistrarQuartoRequest registrarQuartoRequest () {
        return new RegistrarQuartoRequest(
                "Quarto com 1 cama de casal e frigobar",
                1
        );
    }
}
