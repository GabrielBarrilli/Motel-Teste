package org.gabrielbarrilli.motelteste.model;

import jakarta.persistence.*;
import org.gabrielbarrilli.motelteste.Enum.StatusEntrada;
import org.gabrielbarrilli.motelteste.Enum.StatusPagamento;
import org.gabrielbarrilli.motelteste.Enum.TipoPagamento;

import java.time.*;

@Entity
@Table(name = "mt02_entrada")
public class Entrada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mt02_codigo_entrada")
    private Long id;

    @Column(name = "mt02_data_registro_entrada")
    private LocalDate dataRegistroEntrada;

    @Column(name = "mt02_status_entrada")
    private StatusEntrada statusEntrada;

    @Column(name = "mt02_tipo_pagamento")
    private TipoPagamento tipoPagamento;

    @Column(name = "mt02_placa")
    private String placa;

    @Column(name = "mt02_hora_saida")
    private LocalTime horaSaida;

    @ManyToOne
    @JoinColumn(name = "fkmt02mt03_codigo_quartos")
    private Quartos quartos;

    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;

    @Column(name = "mt02_total_entrada")
    private Float totalEntrada;

    @Column(name = "mt02_hora_entrada")
    private LocalTime horaEntrada;

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
}