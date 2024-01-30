package org.gabrielbarrilli.motelteste.model.builders;

import org.gabrielbarrilli.motelteste.enums.StatusDoQuarto;
import org.gabrielbarrilli.motelteste.model.Quartos;

public class QuartosBuilder {

    private String descricao;
    private Integer capacidadePessoa;
    private StatusDoQuarto statusDoQuarto;

    public QuartosBuilder descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public QuartosBuilder capacidadePessoa(Integer capacidadePessoa) {
        this.capacidadePessoa = capacidadePessoa;
        return this;
    }

    public QuartosBuilder statusDoQuarto(StatusDoQuarto statusDoQuarto) {
        this.statusDoQuarto = statusDoQuarto;
        return this;
    }

    public Quartos build() {
        Quartos quartos = new Quartos();

        quartos.setDescricao(descricao);
        quartos.setCapacidadePessoa(capacidadePessoa);
        quartos.setStatusDoQuarto(statusDoQuarto);

        return quartos;
    }
}
