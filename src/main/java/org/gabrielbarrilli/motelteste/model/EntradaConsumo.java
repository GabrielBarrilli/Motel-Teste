package org.gabrielbarrilli.motelteste.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mt04_entrada_consumo")
public class EntradaConsumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mt04_codigo_entrada_produto")
    private Long id;

    @Column(name = "mt04_total")
    private Float total;

    @OneToOne
    @JoinColumn(name = "fkmt04mt05_codigo_itens")
    private Itens itens;

    @Column(name = "mt04_quantidade")
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "fkmt04mt02_codigo_entrada")
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
}

