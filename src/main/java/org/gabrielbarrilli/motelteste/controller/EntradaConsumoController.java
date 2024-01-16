package org.gabrielbarrilli.motelteste.controller;

import org.gabrielbarrilli.motelteste.model.EntradaConsumo;
import org.gabrielbarrilli.motelteste.model.Itens;
import org.gabrielbarrilli.motelteste.response.ConsumoRequest;
import org.gabrielbarrilli.motelteste.service.EntradaConsumoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api")
public class EntradaConsumoController {

    private final EntradaConsumoService entradaConsumoService;

    public EntradaConsumoController(EntradaConsumoService entradaConsumoService) {
        this.entradaConsumoService = entradaConsumoService;
    }

    @GetMapping("/findAllEntradaConsumo")
    public List<EntradaConsumo> findAllEntradaConsumo(){
        return entradaConsumoService.findAllEntradaConsumo();
    }

    @PostMapping("/createEntradaConsumo")
    public EntradaConsumo createEntradaConsumo(ConsumoRequest consumoRequest, Long idEntrada, Long idItens) {
        return entradaConsumoService.createEntradaConsumo(consumoRequest, idEntrada, idItens);
    }
}
