package org.gabrielbarrilli.motelteste.controller;

import jakarta.persistence.EntityNotFoundException;
import org.gabrielbarrilli.motelteste.response.EntradaResponse;
import org.gabrielbarrilli.motelteste.response.MapaGeralResponse;
import org.gabrielbarrilli.motelteste.service.MapaGeralService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api")
public class MapaGeralController {

    private final MapaGeralService mapaGeralService;

    public MapaGeralController(MapaGeralService mapaGeralService) {
        this.mapaGeralService = mapaGeralService;
    }

    @GetMapping("/findAllMapas")
    public ResponseEntity<List<MapaGeralResponse>> getAllMapaGeral() {
        List<MapaGeralResponse> mapaGeralResponse = mapaGeralService.getAllMapaGeral();
        return ResponseEntity.ok(mapaGeralResponse);
    }

    @GetMapping("/buscaMapaPorId/{id}")
    public MapaGeralResponse findMapaGeralById(@PathVariable Long id) {
        return mapaGeralService.findMapaGeralById(id);
    }
}
