package org.gabrielbarrilli.motelteste.fixture;

import org.gabrielbarrilli.motelteste.enums.StatusDoQuarto;
import org.gabrielbarrilli.motelteste.model.Quartos;

public class QuartosFixture {
    public static Quartos quarto1() {

        return new Quartos(
                1L,
                1L,
                "Quarto com 1 cama de casal e frigobar",
                1,
                StatusDoQuarto.DISPONIVEL
        );
    }

    public static Quartos quarto2() {

        return new Quartos(
                2L,
                2L,
                "Quarto com 2 camas de casal e frigobar",
                2,
                StatusDoQuarto.DISPONIVEL
        );
    }
}
