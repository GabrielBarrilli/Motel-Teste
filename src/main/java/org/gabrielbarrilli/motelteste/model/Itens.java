package org.gabrielbarrilli.motelteste.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mt04_itens")
public class Itens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mt04_codigo_itens")
    private Long id;

    @Column(name = "mt04_descricao")
    private String nomeItem;

    @Column(name = "mt04_valor")
    private Float valor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Itens(String nomeItem, Float valor) {
        this.nomeItem = nomeItem;
        this.valor = valor;
    }

    public Itens() {
    }
}