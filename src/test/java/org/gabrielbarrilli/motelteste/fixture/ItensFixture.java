package org.gabrielbarrilli.motelteste.fixture;

import org.gabrielbarrilli.motelteste.model.Itens;

public class ItensFixture {

    public static Itens item () {
        return new Itens(
                "maçã",
                10F
        );
    }
}
