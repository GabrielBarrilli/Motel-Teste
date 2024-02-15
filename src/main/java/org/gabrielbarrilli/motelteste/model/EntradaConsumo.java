package org.gabrielbarrilli.motelteste.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mt03_entrada_consumo")
public class EntradaConsumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mt03_codigo_entrada_produto")
    private Long id;

    @Column(name = "mt03_total")
    private Float total;

    @ManyToOne
    @JoinColumn(name = "fkmt03mt04_codigo_itens")
    private Itens itens;

    @Column(name = "mt03_quantidade")
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "fkmt03mt01_codigo_entrada")
    private Entrada entrada;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Itens getItens() {
        return itens;
    }

    public void setItens(Itens itens) {
        this.itens = itens;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    public EntradaConsumo(Float total, Itens itens, Integer quantidade, Entrada entrada) {
        this.total = total;
        this.itens = itens;
        this.quantidade = quantidade;
        this.entrada = entrada;
    }

    public EntradaConsumo() {
    }

    public EntradaConsumo(Long id, Float total, Itens itens, Integer quantidade, Entrada entrada) {
        this.id = id;
        this.total = total;
        this.itens = itens;
        this.quantidade = quantidade;
        this.entrada = entrada;
    }
}

