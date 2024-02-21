package org.gabrielbarrilli.motelteste.controller;

import org.gabrielbarrilli.motelteste.model.MapaGeral;
import org.gabrielbarrilli.motelteste.model.request.AlterarValorRequest;
import org.gabrielbarrilli.motelteste.model.response.MapaGeralResponse;
import org.gabrielbarrilli.motelteste.service.MapaGeralService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MapaGeralController {

    private final MapaGeralService mapaGeralService;

    public MapaGeralController(MapaGeralService mapaGeralService) {
        this.mapaGeralService = mapaGeralService;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/alterarValorDoCaixa")
    public MapaGeral alterarValor(@RequestBody AlterarValorRequest alterarValorRequest) {
        return mapaGeralService.alterarValor(alterarValorRequest);
    }

    @GetMapping("/findAllMapas")
    public ResponseEntity<List<MapaGeral>> getAllMapaGeral() {
        List<MapaGeral> mapaGeralResponse = mapaGeralService.getAllMapaGeral();
        return ResponseEntity.ok(mapaGeralResponse);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/buscaMapaPorId/{id}")
    public MapaGeralResponse findMapaGeralById(@PathVariable Long id) {
        return mapaGeralService.findMapaGeralById(id);
    }
}
