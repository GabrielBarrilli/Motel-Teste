package org.gabrielbarrilli.motelteste.model;

import jakarta.persistence.*;
import org.gabrielbarrilli.motelteste.Enum.StatusDoQuarto;

@Entity
@Table(name = "mt03_quartos")
public class Quartos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mt03_codigo_quartos")
    private Long id;

    @Column(name = "mt03_numero")
    private Integer numero;

    @Column(name = "mt03_descricao")
    private String descricao;

    @Column(name = "mt03_capacidade_pessoa")
    private Integer capacidadePessoa;

    @Enumerated(EnumType.STRING)
    private StatusDoQuarto statusDoQuarto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getCapacidadePessoa() {
        return capacidadePessoa;
    }

    public void setCapacidadePessoa(Integer capacidadePessoa) {
        this.capacidadePessoa = capacidadePessoa;
    }

    public StatusDoQuarto getStatusDoQuarto() {
        return statusDoQuarto;
    }

    public void setStatusDoQuarto(StatusDoQuarto statusDoQuarto) {
        this.statusDoQuarto = statusDoQuarto;
    }

}