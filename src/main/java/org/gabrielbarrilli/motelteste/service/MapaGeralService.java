package org.gabrielbarrilli.motelteste.service;

import jakarta.persistence.EntityNotFoundException;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.MapaGeral;
import org.gabrielbarrilli.motelteste.model.builders.MapaGeralBuilder;
import org.gabrielbarrilli.motelteste.repository.EntradaRepository;
import org.gabrielbarrilli.motelteste.repository.MapaGeralRepository;
import org.gabrielbarrilli.motelteste.response.MapaGeralResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MapaGeralService {

    private final MapaGeralRepository mapaGeralRepository;
    private final EntradaRepository entradaRepository;

    public MapaGeralService(MapaGeralRepository mapaGeralRepository,
                            EntradaRepository entradaRepository) {
        this.mapaGeralRepository = mapaGeralRepository;
        this.entradaRepository = entradaRepository;
    }

    private MapaGeralResponse mapaGeralResponse(MapaGeral mapaGeral) {

        return new MapaGeralResponse(
                mapaGeral.getId(),
                mapaGeral.getEntrada(),
                mapaGeral.getReport(),
                mapaGeral.getSaida(),
                mapaGeralRepository.calculaTotal(),
                mapaGeral.getHora(),
                mapaGeral.getData());
    }

    public List<MapaGeral> getAllMapaGeral() {
        List<MapaGeral> mapaGeral = mapaGeralRepository.findAll();
        List<MapaGeralResponse> mapaGeralResponse = new ArrayList<>();

        mapaGeral.forEach(mapaGeral1 -> {
            var response = mapaGeralResponse(mapaGeral1);
            mapaGeralResponse.add(response);
        });
        return mapaGeral;
    }

    public MapaGeralResponse findMapaGeralById(Long id) {
        var mapa = mapaGeralRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Mapa não encontrado, digite outro id!"));
        return mapaGeralResponse(mapa);
    }

    public MapaGeral alterarValor(MapaGeral mapaGeral) {

        mapaGeral.setData(LocalDate.now());
        mapaGeral.setHora(LocalTime.now());

        if (mapaGeral.getEntrada() >= 0) {
            String msg = ("Foi adicionado " + mapaGeral.getEntrada() + " reais ao caixa");
            var total = mapaGeralRepository.calculaTotal();
            mapaGeral.setTotal(total + mapaGeral.getEntrada());
            mapaGeral.setReport(msg);
            mapaGeral.setSaida(0F);
        } else {
            String msg = ("Foi retirado " + mapaGeral.getEntrada() + " reais do caixa");
            mapaGeral.setSaida(mapaGeral.getEntrada());
            var total = mapaGeralRepository.calculaTotal();
            mapaGeral.setTotal(total + mapaGeral.getEntrada());
            mapaGeral.setReport(msg);
            mapaGeral.setEntrada(0F);
        }
        return mapaGeralRepository.save(mapaGeral);
    }

    public void criarMapa(Long idEntrada) {
        Entrada entrada = entradaRepository.findById(idEntrada).
                orElseThrow(() -> new EntityNotFoundException("Não existe essa entrada"));

        Float totalMap = mapaGeralRepository.calculaTotal();
        if (totalMap == null) {
            totalMap = 0F;
        }

        Float totalEntrada = 0F;
        String periodo = "";
        String relatorio = "";

        LocalTime horarioAtual = LocalTime.now();

        LocalTime inicioMadrugada = LocalTime.of(0, 0);
        LocalTime fimMadrugada = LocalTime.of(5, 59, 59);

        LocalTime inicioDia = LocalTime.of(6, 0);
        LocalTime fimDia = LocalTime.of(17, 59, 59);

        LocalTime inicioNoite = LocalTime.of(18, 0);
        LocalTime fimNoite = LocalTime.of(23, 59, 59);

        if (horarioAtual.isAfter(inicioDia) && horarioAtual.isBefore(fimDia)) {
            periodo = "ENTRADA DIA";
        }
        if (horarioAtual.isAfter(inicioNoite) && horarioAtual.isBefore(fimNoite) || horarioAtual.isAfter(inicioMadrugada) && horarioAtual.isBefore(fimMadrugada)) {
            periodo = "ENTRADA NOITE";
        }

        switch (entrada.getTipoPagamento()) {
            case PIX:
                relatorio = periodo + " (PIX)";
                totalEntrada = 0F;
                totalMap += 0;
                break;
            case CARTAO:
                relatorio = periodo + " (CARTAO)";
                totalEntrada = 0F;
                totalMap += 0;
                break;
            case DINHEIRO:
                relatorio = periodo + " (DINHEIRO)";
                var valorPago = entrada.getTotalEntrada();
                totalEntrada = valorPago;
                totalMap += valorPago;
                break;
        }

        MapaGeral mapaGeral = new MapaGeralBuilder().
                entrada(totalEntrada).
                saida(0F).
                total(totalMap).
                data(LocalDate.now()).
                hora(LocalTime.now()).
                apartamento(0).
                build();

        mapaGeralRepository.save(mapaGeral);

    }
}
