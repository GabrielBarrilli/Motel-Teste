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
                mapaGeral.getData(),
                mapaGeral.getIdEntrada().getId()
        );
    }

    public List<MapaGeralResponse> getAllMapaGeral() {
        List<MapaGeral> mapaGeral = mapaGeralRepository.findAll();
        List<MapaGeralResponse> mapaGeralResponse = new ArrayList<>();

        mapaGeral.forEach(mapaGeral1 -> {
            var response = mapaGeralResponse(mapaGeral1);
            mapaGeralResponse.add(response);
        });
        return mapaGeralResponse;
    }

    public MapaGeralResponse findMapaGeralById(Long id) {
        var mapa = mapaGeralRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Mapa não encontrado, digite outro id!"));
        return mapaGeralResponse(mapa);
    }

    public void criarMapa(Long idEntrada) {
        MapaGeral mapaGeral = new MapaGeral();
        Entrada entrada = entradaRepository.findById(idEntrada).
                orElseThrow(()-> new EntityNotFoundException("Não existe essa entrada"));

        if (mapaGeral.getTotal() == null){
            mapaGeral.setTotal(0F);
        }

        float totalMap = 0;

        switch (entrada.getTipoPagamento()) {
            case PIX:
                mapaGeral.setReport("Pagamento feito via pix!");
                mapaGeral.setEntrada(0F);
                totalMap += 0;
                break;
            case CARTAO:
                mapaGeral.setReport("Pagamento feito via cartão!");
                mapaGeral.setEntrada(0F);
                totalMap += 0;
                break;
            case DINHEIRO:
                mapaGeral.setReport("Pagamento feito via dinheiro!");
                var valorPago = entrada.getTotalEntrada();
                mapaGeral.setEntrada(valorPago);
                totalMap += valorPago;
                break;
        }

        mapaGeral.setTotal(totalMap);
        mapaGeral.setIdEntrada(entrada);
        mapaGeral.setData(LocalDate.now());
        mapaGeral.setHora(LocalTime.now());

        mapaGeralRepository.save(mapaGeral);
    }
}
