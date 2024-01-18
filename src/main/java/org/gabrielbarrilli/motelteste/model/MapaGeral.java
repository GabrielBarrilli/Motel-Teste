package org.gabrielbarrilli.motelteste.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "mt05_mapa_geral")
public class MapaGeral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mt05_codigo_mapa_geral")
    private Long id;

    @Column(name = "mt05_apartamento")
    private Integer apartamento;

    @Column(name = "mt05_entrada")
    private Float entrada;

    @Column(name = "mt05_report")
    private String report;

    @Column(name = "mt05_saida")
    private Float saida;

    @Column(name = "mt05_total")
    private Float total;

    @Column(name = "mt05_hora")
    private LocalTime hora;

    @Column(name = "mt05_data")
    private LocalDate data;

    @OneToOne
    @JoinColumn(name = "fk0501_codigo_entrada")
    private Entrada idEntrada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getApartamento() {
        return apartamento;
    }

    public void setApartamento(Integer apartamento) {
        this.apartamento = apartamento;
    }

    public Float getEntrada() {
        return entrada;
    }

    public void setEntrada(Float entrada) {
        this.entrada = entrada;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public Float getSaida() {
        return saida;
    }

    public void setSaida(Float saida) {
        this.saida = saida;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Entrada getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(Entrada idEntrada) {
        this.idEntrada = idEntrada;
    }

    public MapaGeral(Integer apartamento, Float entrada, String report, Float saida, Float total, LocalTime hora, LocalDate data, Entrada idEntrada) {
        this.apartamento = apartamento;
        this.entrada = entrada;
        this.report = report;
        this.saida = saida;
        this.total = total;
        this.hora = hora;
        this.data = data;
        this.idEntrada = idEntrada;
    }

    public MapaGeral() {
    }
}