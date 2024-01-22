package org.gabrielbarrilli.motelteste.controller;

import org.gabrielbarrilli.motelteste.model.EntradaConsumo;
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

    @GetMapping("/findAllEntradaConsumoByEntradaId/{idEntrada}")
    public List<EntradaConsumo> findAllEntradaConsumoByEntradaId(@PathVariable Long idEntrada) {
        return entradaConsumoService.findAllEntradaConsumoByEntradaId(idEntrada);
    }

    @PostMapping("/createEntradaConsumo")
    public EntradaConsumo createEntradaConsumo(ConsumoRequest consumoRequest, Long idEntrada, Long idItens) {
        return entradaConsumoService.createEntradaConsumo(consumoRequest, idEntrada, idItens);
    }

    @DeleteMapping("/deletarConsumo")
    public void deleteConsumo(Long idConsumo) {
        entradaConsumoService.deleteConsumo(idConsumo);
    }
}
