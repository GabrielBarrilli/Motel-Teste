package org.gabrielbarrilli.motelteste.model;

import jakarta.persistence.*;

@Entity
public class EntradaConsumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float total;

    @OneToOne
    private ItensModel itens;

    private Integer quantidade;

    @ManyToOne
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

    public ItensModel getItens() {
        return itens;
    }

    public void setItens(ItensModel itens) {
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
}

