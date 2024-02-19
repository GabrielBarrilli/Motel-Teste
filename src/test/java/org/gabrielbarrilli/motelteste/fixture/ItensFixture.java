package org.gabrielbarrilli.motelteste.fixture;

import org.gabrielbarrilli.motelteste.model.Itens;

import java.util.ArrayList;
import java.util.List;

public class ItensFixture {

    public static Itens item1() {
        return new Itens(
                1L,
                "maçã",
                10F
        );
    }

    public static Itens item2() {
        return new Itens(
                2L,
                "maçã",
                10F
        );
    }

    public static List<Itens> itensList() {

        List<Itens> listaItens= new ArrayList<>();

        listaItens.add(item1());
        listaItens.add(item2());

        return listaItens;
    }
}
