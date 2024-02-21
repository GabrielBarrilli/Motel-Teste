package org.gabrielbarrilli.motelteste.fixture;

import org.gabrielbarrilli.motelteste.enums.StatusEntrada;
import org.gabrielbarrilli.motelteste.enums.StatusPagamento;
import org.gabrielbarrilli.motelteste.enums.TipoPagamento;
import org.gabrielbarrilli.motelteste.model.Entrada;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EntradaFixture {

    public static Entrada entradaAtiva() {

        return new Entrada(
                1L,
                "Locador teste",
                LocalDate.now().minusDays(1),
                LocalTime.now(),
                StatusEntrada.ATIVA,
                TipoPagamento.PENDENTE,
                "Placa teste",
                null,
                null,
                QuartosFixture.quartoDisponivel(),
                StatusPagamento.PENDENTE,
                100F
        );
    }

    public static Entrada entradaAtivaDiferente() {

        return new Entrada(
                2L,
                "Locador teste",
                LocalDate.now().minusDays(1),
                LocalTime.now(),
                StatusEntrada.ATIVA,
                TipoPagamento.PENDENTE,
                "Placa teste",
                null,
                null,
                QuartosFixture.quartoDisponivel(),
                StatusPagamento.PENDENTE,
                0F
        );
    }

    public static Entrada entradaParaController1(){

        return new Entrada(
                1L,
                "Locador teste",
                LocalDate.now().minusDays(1),
                LocalTime.of(10,10,10),
                StatusEntrada.ATIVA,
                TipoPagamento.PENDENTE,
                "Placa teste",
                null,
                null,
                QuartosFixture.quartoDisponivel(),
                StatusPagamento.PENDENTE,
                100F
        );
    }

    public static Entrada entradaParaController2(){

        return new Entrada(
                4L,
                "Locador teste",
                LocalDate.now().minusDays(1),
                LocalTime.of(10,10,10),
                StatusEntrada.ATIVA,
                TipoPagamento.PENDENTE,
                "Placa teste",
                LocalDate.now(),
                LocalTime.of(10,10,10),
                QuartosFixture.quartoDisponivel(),
                StatusPagamento.PENDENTE,
                0F
        );
    }

    public static Entrada entradaParaController3(){

        return new Entrada(
                4L,
                "Locador teste",
                LocalDate.now().minusDays(1),
                LocalTime.of(10,10,10),
                StatusEntrada.ATIVA,
                TipoPagamento.PENDENTE,
                "Placa teste",
                null,
                null,
                QuartosFixture.quartoDisponivel(),
                StatusPagamento.PENDENTE,
                100F
        );
    }

    public static Entrada entradaFinalizada() {

        return new Entrada(
                4L,
                "Locador teste",
                LocalDate.now().minusDays(1),
                LocalTime.now(),
                StatusEntrada.FINALIZADA,
                TipoPagamento.PENDENTE,
                "Placa teste",
                null,
                null,
                QuartosFixture.quartoDisponivel(),
                StatusPagamento.PENDENTE,
                0F
        );
    }

    public static List<Entrada> entradasList() {

        List<Entrada> entradasList = new ArrayList<>();

        entradasList.add(entradaAtiva());
        entradasList.add(entradaFinalizada());

        return entradasList;
    }

}
