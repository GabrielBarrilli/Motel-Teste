package org.gabrielbarrilli.motelteste.fixture;


import org.gabrielbarrilli.motelteste.model.EntradaConsumo;

import java.util.ArrayList;
import java.util.List;

public class EntradaConsumoFixture {

    public static EntradaConsumo entradaConsumo1() {
        return new EntradaConsumo(
                1L,
                100F,
                ItensFixture.item(),
                1,
                EntradaFixture.entradaAtiva()
        );
    }

    public static EntradaConsumo entradaConsumo2() {
        return new EntradaConsumo(
                2L,
                100F,
                ItensFixture.item(),
                1,
                EntradaFixture.entradaAtiva()
        );
    }

    public static List<EntradaConsumo> entradaConsumoList() {

        List<EntradaConsumo> consumoList = new ArrayList<>();

        consumoList.add(entradaConsumo1());
        consumoList.add(entradaConsumo2());

        return consumoList;
    }
}