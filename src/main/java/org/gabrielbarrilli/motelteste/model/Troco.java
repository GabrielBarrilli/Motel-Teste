package org.gabrielbarrilli.motelteste.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mt01_troco")
public class Troco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mt01_codigo_troco")
    private Long id;
    @Column(name = "mt01_valor_entrada")
    private Float valorEntrada;
    @Column(name = "mt01_troco")
    private Float troco;

    @OneToOne
    @JoinColumn(name = "fkmt01mt02_codigo_entrada")
    private Entrada entrada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getValorEntrada() {
        return valorEntrada;
    }

    public void setValorEntrada(Float valorEntrada) {
        this.valorEntrada = valorEntrada;
    }

    public Float getTroco() {
        return troco;
    }

    public void setTroco(Float troco) {
        this.troco = troco;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntradas(Entrada entrada) {
        this.entrada = entrada;
    }

}