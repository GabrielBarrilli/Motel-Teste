package org.gabrielbarrilli.motelteste.model.builders;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.EntradaConsumo;
import org.gabrielbarrilli.motelteste.model.Itens;

public class EntradaConsumoBuilder {

    private Float total;
    private Itens itens;
    private Integer quantidade;
    private Entrada entrada;

    public EntradaConsumoBuilder total(Float total) {
        this.total = total;
        return this;
    }

    public EntradaConsumoBuilder itens(Itens itens) {
        this.itens = itens;
        return this;
    }

    public EntradaConsumoBuilder quantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public EntradaConsumoBuilder entrada(Entrada entrada) {
        this.entrada = entrada;
        return this;
    }

    public EntradaConsumo build() {
        EntradaConsumo entradaConsumo = new EntradaConsumo();

        entradaConsumo.setTotal(total);
        entradaConsumo.setItens(itens);
        entradaConsumo.setQuantidade(quantidade);
        entradaConsumo.setEntrada(entrada);

        return entradaConsumo;
    }
}
