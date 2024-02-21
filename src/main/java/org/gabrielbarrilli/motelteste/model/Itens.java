package org.gabrielbarrilli.motelteste.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "mt04_itens")
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    public Itens(Long id, String nomeItem, Float valor) {
        this.id = id;
        this.nomeItem = nomeItem;
        this.valor = valor;
    }

    public Itens(String nomeItem, Float valor) {
        this.nomeItem = nomeItem;
        this.valor = valor;
    }

    public Itens() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Itens itens = (Itens) o;
        return Objects.equals(id, itens.id) && Objects.equals(nomeItem, itens.nomeItem) && Objects.equals(valor, itens.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nomeItem, valor);
    }
}