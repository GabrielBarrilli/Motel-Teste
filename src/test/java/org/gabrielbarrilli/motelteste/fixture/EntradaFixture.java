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
                "Gabriel",
                LocalDate.now().minusDays(1),
                LocalTime.now(),
                StatusEntrada.ATIVA,
                TipoPagamento.PENDENTE,
                "Placa teste",
                null,
                null,
                QuartosFixture.quarto1(),
                StatusPagamento.PENDENTE,
                0F
        );
    }

    public static Entrada entradaAtivaDiferente() {

        return new Entrada(
                2L,
                "Gabriel 2",
                LocalDate.now().minusDays(1),
                LocalTime.now(),
                StatusEntrada.ATIVA,
                TipoPagamento.PENDENTE,
                "Placa teste",
                null,
                null,
                QuartosFixture.quarto1(),
                StatusPagamento.PENDENTE,
                0F
        );
    }

    public static Entrada entradaDataAtual() {

        return new Entrada(
                3L,
                "Gabriel 3",
                LocalDate.now().minusDays(1),
                LocalTime.now(),
                StatusEntrada.ATIVA,
                TipoPagamento.PENDENTE,
                "Placa teste",
                null,
                null,
                QuartosFixture.quarto1(),
                StatusPagamento.PENDENTE,
                0F
        );
    }

    public static Entrada entradaFinalizada() {

        return new Entrada(
                4L,
                "Gabriel",
                LocalDate.now().minusDays(1),
                LocalTime.now(),
                StatusEntrada.FINALIZADA,
                TipoPagamento.PENDENTE,
                "Placa teste",
                null,
                null,
                QuartosFixture.quarto1(),
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

    public static List<Entrada> entradasListAtiva() {

        List<Entrada> entradasList = new ArrayList<>();

        entradasList.add(entradaAtiva());
        entradasList.add(entradaAtivaDiferente());

        return entradasList;
    }

    public static List<Entrada> entradasListDataAtual() {

        List<Entrada> entradasList = new ArrayList<>();

        entradasList.add(entradaAtivaDiferente());
        entradasList.add(entradaDataAtual());

        return entradasList;
    }

}
