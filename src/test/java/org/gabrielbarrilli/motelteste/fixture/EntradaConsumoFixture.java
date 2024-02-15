package org.gabrielbarrilli.motelteste.fixture;


import org.gabrielbarrilli.motelteste.model.EntradaConsumo;

import java.util.ArrayList;
import java.util.List;

public class EntradaConsumoFixture {

    public static EntradaConsumo entradaConsumoInicial() {
        return new EntradaConsumo(
                //number.longValue(),
                1L,
                100F,
                ItensFixture.item(),
                1,
                EntradaFixture.entradaAtiva()
        );
    }

    public static List<EntradaConsumo> entradaConsumoList() {

        List<EntradaConsumo> consumoList = new ArrayList<>();

            consumoList.add(entradaConsumoInicial());

        return consumoList;
    }
}