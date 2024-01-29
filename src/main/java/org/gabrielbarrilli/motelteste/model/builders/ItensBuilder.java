package org.gabrielbarrilli.motelteste.model.builders;

import org.gabrielbarrilli.motelteste.model.Itens;

public class ItensBuilder {

    private String nomeItem;
    private Float valor;

    public ItensBuilder nomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
        return this;
    }

    public ItensBuilder valor(Float valor) {
        this.valor = valor;
        return this;
    }

    public Itens build() {
        Itens itens = new Itens();

        itens.setNomeItem(nomeItem);
        itens.setValor(valor);

        return itens;
    }
}
