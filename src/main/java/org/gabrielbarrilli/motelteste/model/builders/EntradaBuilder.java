package org.gabrielbarrilli.motelteste.model.builders;

import jakarta.persistence.*;
import org.gabrielbarrilli.motelteste.Enum.StatusEntrada;
import org.gabrielbarrilli.motelteste.Enum.StatusPagamento;
import org.gabrielbarrilli.motelteste.Enum.TipoPagamento;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.Quartos;

import java.time.LocalDate;
import java.time.LocalTime;

public class EntradaBuilder {

    private String nomeLocador;
    private LocalDate dataRegistroEntrada;
    private LocalTime horaEntrada;
    private StatusEntrada statusEntrada;
    private TipoPagamento tipoPagamento;
    private String placa;
    private LocalDate dataSaida;
    private LocalTime horaSaida;
    private Quartos quartos;
    private StatusPagamento statusPagamento;
    private Float totalEntrada;

    public EntradaBuilder nomeLocador(String nomeLocador) {
        this.nomeLocador = nomeLocador;
        return this;
    }

    public EntradaBuilder dataRegistroEntrada(LocalDate dataRegistroEntrada) {
        this.dataRegistroEntrada = dataRegistroEntrada;
        return this;
    }

    public EntradaBuilder horaEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
        return this;
    }

    public EntradaBuilder statusEntrada(StatusEntrada statusEntrada) {
        this.statusEntrada = statusEntrada;
        return this;
    }

    public EntradaBuilder tipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
        return this;
    }

    public EntradaBuilder placa(String placa) {
        this.placa = placa;
        return this;
    }

    public EntradaBuilder dataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
        return this;
    }

    public EntradaBuilder horaSaida(LocalTime horaSaida) {
        this.horaSaida = horaSaida;
        return this;
    }

    public EntradaBuilder quartos(Quartos quartos) {
        this.quartos = quartos;
        return this;
    }

    public EntradaBuilder statusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
        return this;
    }

    public EntradaBuilder totalEntrada(Float totalEntrada) {
        this.totalEntrada = totalEntrada;
        return this;
    }

    public Entrada build(){
        Entrada entrada = new Entrada();
        entrada.setNomeLocador(nomeLocador);
        entrada.setDataRegistroEntrada(dataRegistroEntrada);
        entrada.setHoraEntrada(horaEntrada);
        entrada.setHoraEntrada(horaEntrada);
        entrada.setStatusEntrada(statusEntrada);
        entrada.setTipoPagamento(tipoPagamento);
        entrada.setPlaca(placa);
        entrada.setDataSaida(dataSaida);
        entrada.setHoraSaida(horaSaida);
        entrada.setQuartos(quartos);
        entrada.setStatusPagamento(statusPagamento);
        entrada.setTotalEntrada(totalEntrada);

        return entrada;
    }
}
