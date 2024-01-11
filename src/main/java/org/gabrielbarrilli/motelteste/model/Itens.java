package org.gabrielbarrilli.motelteste.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mt05_itens")
public class Itens {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mt05_codigo_itens")
    private Long id;

    @Column(name = "mt05_descricao")
    private String descricao;

    @Column(name = "mt05_valor")
    private Float valor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    // getters and setters
}