package org.gabrielbarrilli.motelteste.fixture;

import org.gabrielbarrilli.motelteste.enums.StatusDoQuarto;
import org.gabrielbarrilli.motelteste.model.Quartos;

import java.util.ArrayList;
import java.util.List;

public class QuartosFixture {
    public static Quartos quartoDisponivel() {

        return new Quartos(
                1L,
                1L,
                "Quarto com 1 cama de casal e frigobar",
                1,
                StatusDoQuarto.DISPONIVEL
        );
    }

    public static Quartos quartoOcupado() {

        return new Quartos(
                2L,
                2L,
                "Quarto com 2 camas de casal e frigobar",
                2,
                StatusDoQuarto.DISPONIVEL
        );
    }

    public static List<Quartos> quartosList () {
        List<Quartos> quartosList = new ArrayList<>();

        quartosList.add(quartoDisponivel());
        quartosList.add(quartoOcupado());

        return quartosList;
    }
}
