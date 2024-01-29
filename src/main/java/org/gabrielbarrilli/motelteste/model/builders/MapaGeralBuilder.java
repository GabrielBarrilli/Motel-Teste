package org.gabrielbarrilli.motelteste.model.builders;

import org.gabrielbarrilli.motelteste.model.MapaGeral;

import java.time.LocalDate;
import java.time.LocalTime;

public class MapaGeralBuilder {

    private Integer apartamento;
    private Float entrada;
    private String report;
    private Float saida;
    private Float total;
    private LocalTime hora;
    private LocalDate data;

    public MapaGeralBuilder apartamento(Integer apartamento) {
        this.apartamento = apartamento;
        return this;
    }

    public MapaGeralBuilder entrada(Float entrada) {
        this.entrada = entrada;
        return this;
    }

    public MapaGeralBuilder report(String report) {
        this.report = report;
        return this;
    }

    public MapaGeralBuilder saida(Float saida) {
        this.saida = saida;
        return this;
    }

    public MapaGeralBuilder total(Float total) {
        this.total = total;
        return this;
    }

    public MapaGeralBuilder hora(LocalTime hora) {
        this.hora = hora;
        return this;
    }

    public MapaGeralBuilder data(LocalDate data) {
        this.data = data;
        return this;
    }

    public MapaGeral build() {
        MapaGeral mapaGeral = new MapaGeral();

        mapaGeral.setApartamento(apartamento);
        mapaGeral.setEntrada(entrada);
        mapaGeral.setReport(report);
        mapaGeral.setSaida(saida);
        mapaGeral.setTotal(total);
        mapaGeral.setHora(hora);
        mapaGeral.setData(data);

        return mapaGeral;
    }
}
