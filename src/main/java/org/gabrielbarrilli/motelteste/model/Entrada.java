package org.gabrielbarrilli.motelteste.model;

import jakarta.persistence.*;
import org.gabrielbarrilli.motelteste.Enum.StatusEntrada;
import org.gabrielbarrilli.motelteste.Enum.StatusPagamento;
import org.gabrielbarrilli.motelteste.Enum.TipoPagamento;

import java.time.*;

@Entity
@Table(name = "mt01_entrada")
public class Entrada {

    //testandoGit
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mt01_codigo_entrada")
    private Long id;

    @Column(name = "mt01_nome_locador")
    private String nomeLocador;

    @Column(name = "mt01_data_registro_entrada")
    private LocalDate dataRegistroEntrada;

    @Column(name = "mt01_hora_entrada")
    private LocalTime horaEntrada;

    @Column(name = "mt01_status_entrada")
    @Enumerated(EnumType.STRING)
    private StatusEntrada statusEntrada;

    @Column(name = "mt01_tipo_pagamento")
    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;

    @Column(name = "mt01_placa")
    private String placa;

    @Column(name = "mt01_data_saida")
    private LocalDate dataSaida;

    @Column(name = "mt01_hora_saida")
    private LocalTime horaSaida;

    @ManyToOne
    @JoinColumn(name = "fkmt01mt02_codigo_quartos")
    private Quartos quartos;

    @Column(name = "mt01_status_pagamento")
    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;

    @Column(name = "mt01_total_entrada")
    private Float totalEntrada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataRegistroEntrada() {
        return dataRegistroEntrada;
    }

    public void setDataRegistroEntrada(LocalDate dataRegistroEntrada) {
        this.dataRegistroEntrada = dataRegistroEntrada;
    }

    public StatusEntrada getStatusEntrada() {
        return statusEntrada;
    }

    public void setStatusEntrada(StatusEntrada statusEntrada) {
        this.statusEntrada = statusEntrada;
    }

    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public LocalTime getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(LocalTime horaSaida) {
        this.horaSaida = horaSaida;
    }

    public Quartos getQuartos() {
        return quartos;
    }

    public void setQuartos(Quartos quartos) {
        this.quartos = quartos;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public Float getTotalEntrada() {
        return totalEntrada;
    }

    public void setTotalEntrada(Float totalEntrada) {
        this.totalEntrada = totalEntrada;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getNomeLocador() {
        return nomeLocador;
    }

    public void setNomeLocador(String nomeLocador) {
        this.nomeLocador = nomeLocador;
    }

    public LocalDate getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    public Entrada(String nomeLocador, LocalDate dataRegistroEntrada, LocalTime horaEntrada, StatusEntrada statusEntrada, TipoPagamento tipoPagamento, String placa, LocalDate dataSaida, LocalTime horaSaida, Quartos quartos, StatusPagamento statusPagamento, Float totalEntrada) {
        this.nomeLocador = nomeLocador;
        this.dataRegistroEntrada = dataRegistroEntrada;
        this.horaEntrada = horaEntrada;
        this.statusEntrada = statusEntrada;
        this.tipoPagamento = tipoPagamento;
        this.placa = placa;
        this.dataSaida = dataSaida;
        this.horaSaida = horaSaida;
        this.quartos = quartos;
        this.statusPagamento = statusPagamento;
        this.totalEntrada = totalEntrada;
    }

    public Entrada() {
    }
}