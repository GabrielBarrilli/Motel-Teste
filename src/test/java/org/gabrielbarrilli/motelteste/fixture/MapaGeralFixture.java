package org.gabrielbarrilli.motelteste.fixture;

import org.gabrielbarrilli.motelteste.model.MapaGeral;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MapaGeralFixture {

    public static MapaGeral mapaGeralNegativo() {
        return new MapaGeral(
                1L,
                1,
                -100F,
                "ENTRADA DIA (DINHEIRO)",
                0F,
                0F,
                LocalTime.of(10, 10),
                LocalDate.now()

        );
    }

    public static MapaGeral mapaGeralDinheiroDia() {
        return new MapaGeral(
                1L,
                1,
                100F,
                "ENTRADA DIA (DINHEIRO)",
                0F,
                0F,
                LocalTime.of(10, 10),
                LocalDate.now()

        );
    }

    public static MapaGeral mapaGeralPixDia() {
        return new MapaGeral(
                1L,
                1,
                100F,
                "ENTRADA DIA (PIX)",
                0F,
                0F,
                LocalTime.of(10, 10),
                LocalDate.now()

        );
    }

    public static MapaGeral mapaGeralCartaoDia() {
        return new MapaGeral(
                1L,
                1,
                100F,
                "ENTRADA DIA (CARTAO)",
                0F,
                0F,
                LocalTime.of(10, 10),
                LocalDate.now()

        );
    }

    public static MapaGeral mapaGeralDinheiroNoite() {
        return new MapaGeral(
                2L,
                1,
                100F,
                "ENTRADA NOITE (DINHEIRO)",
                0F,
                0F,
                LocalTime.of(20, 20),
                LocalDate.now()

        );
    }

    public static MapaGeral mapaGeralPixNoite() {
        return new MapaGeral(
                2L,
                1,
                100F,
                "ENTRADA NOITE (PIX)",
                0F,
                0F,
                LocalTime.of(20, 20),
                LocalDate.now()

        );
    }

    public static MapaGeral mapaGeralCartaoNoite() {
        return new MapaGeral(
                2L,
                1,
                100F,
                "ENTRADA NOITE (CARTAO)",
                0F,
                0F,
                LocalTime.of(20, 20),
                LocalDate.now()

        );
    }

    public static List<MapaGeral> mapaGeralList() {

        List<MapaGeral> mapaGeralList = new ArrayList<>();

        mapaGeralList.add(mapaGeralDinheiroDia());
        mapaGeralList.add(mapaGeralDinheiroNoite());

        return mapaGeralList;
    }
}
