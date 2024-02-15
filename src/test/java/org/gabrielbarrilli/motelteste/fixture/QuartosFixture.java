package org.gabrielbarrilli.motelteste.fixture;

import org.gabrielbarrilli.motelteste.enums.StatusDoQuarto;
import org.gabrielbarrilli.motelteste.model.Quartos;

public class QuartosFixture {
    public static Quartos quarto() {

        return new Quartos(
                1L,
                1L,
                "Quarto com 1 cama de casal e frigobar",
                2,
                StatusDoQuarto.DISPONIVEL
        );
    }

}
