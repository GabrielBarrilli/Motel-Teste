package org.gabrielbarrilli.motelteste.service;

import jakarta.persistence.EntityNotFoundException;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.MapaGeral;
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

    public MapaGeral aterarValor(MapaGeral mapaGeral){

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
        MapaGeral mapaGeral = new MapaGeral();
        Entrada entrada = entradaRepository.findById(idEntrada).
                orElseThrow(()-> new EntityNotFoundException("Não existe essa entrada"));

        if (mapaGeral.getTotal() == null){
            mapaGeral.setTotal(0F);
        }

        Float totalMap = mapaGeralRepository.calculaTotal();
        if (totalMap == null) {
            totalMap = 0F;
        }

        String relatorio = "";


        LocalTime horarioAtual = LocalTime.now();

        LocalTime inicioMadrugada = LocalTime.of(0, 0);
        LocalTime fimMadrugada = LocalTime.of(5, 59, 59);

        LocalTime inicioDia = LocalTime.of(6, 0);
        LocalTime fimDia = LocalTime.of(17, 59, 59);

        LocalTime inicioNoite = LocalTime.of(18, 0);
        LocalTime fimNoite = LocalTime.of(23, 59, 59);

        if (horarioAtual.isAfter(inicioDia) && horarioAtual.isBefore(fimDia)){
            relatorio = "ENTRADA DIA";
        }
        if (horarioAtual.isAfter(inicioNoite) && horarioAtual.isBefore(fimNoite) || horarioAtual.isAfter(inicioMadrugada) && horarioAtual.isBefore(fimMadrugada)){
            relatorio = "ENTRADA NOITE";
        }

        switch (entrada.getTipoPagamento()) {
            case PIX:
                mapaGeral.setReport(relatorio + " (PIX)");
                mapaGeral.setEntrada(0F);
                totalMap += 0;
                break;
            case CARTAO:
                mapaGeral.setReport(relatorio + " (CARTAO)");
                mapaGeral.setEntrada(0F);
                totalMap += 0;
                break;
            case DINHEIRO:
                mapaGeral.setReport(relatorio + " (DINHEIRO)");
                var valorPago = entrada.getTotalEntrada();
                mapaGeral.setEntrada(valorPago);
                totalMap += valorPago;
                break;
        }

        mapaGeral.setSaida(0F);
        mapaGeral.setTotal(totalMap);
        mapaGeral.setData(LocalDate.now());
        mapaGeral.setHora(LocalTime.now());
        mapaGeral.setApartamento(0);

        mapaGeralRepository.save(mapaGeral);
    }
}
