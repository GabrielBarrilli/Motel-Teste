package org.gabrielbarrilli.motelteste.controller;

import jakarta.persistence.EntityNotFoundException;
import org.gabrielbarrilli.motelteste.Enum.StatusDoQuarto;
import org.gabrielbarrilli.motelteste.Enum.StatusEntrada;
import org.gabrielbarrilli.motelteste.Enum.StatusPagamento;
import org.gabrielbarrilli.motelteste.Enum.TipoPagamento;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.Quartos;
import org.gabrielbarrilli.motelteste.request.CriarEntradaRequest;
import org.gabrielbarrilli.motelteste.request.EntradaRequest;
import org.gabrielbarrilli.motelteste.response.EntradaResponse;
import org.gabrielbarrilli.motelteste.service.EntradaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RestController
@RequestMapping("/api")
public class EntradaController {

    private final EntradaService entradaService;

    public EntradaController(EntradaService entradaService) {
        this.entradaService = entradaService;
    }

    @GetMapping("/findAllEntradas")
    public ResponseEntity<List<EntradaResponse>> getAllEntradas() {
        List<EntradaResponse> entradas = entradaService.getAllEntrada();
        return ResponseEntity.ok(entradas);
    }

    @GetMapping("/findEntradaById")
    public EntradaResponse getEntradaById(Long id) {
        return entradaService.getEntradaById(id);
    }

    @GetMapping("/buscaEntradasPorStatusEntrada")
    public List<Entrada> findAllByStatusEntrada(StatusEntrada statusEntrada) {
        return entradaService.findAllByStatusEntrada(statusEntrada);
    }

    @GetMapping("/buscaEntradasPorDataAtual")
    public List<Entrada> findAllByDataRegistroEntrada() {
        return entradaService.findAllByDataRegistroEntrada();
    }

    @GetMapping("/buscaEntradasPorDataDigitada")
    public List<Entrada> findAllByDataRegistroEntrada(@RequestParam("data") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate data) {
        return entradaService.findAllByDataRegistroEntrada(data);
    }

    @PostMapping("/registrarEntrada/{idQuarto}")
    public Entrada createEntrada(@RequestBody CriarEntradaRequest criarEntradaRequest, @PathVariable Long idQuarto){
        return entradaService.createEntrada(criarEntradaRequest, idQuarto);
    }

    @GetMapping("/finalizarEntrada")
    public EntradaResponse finalizarEntrada(Long idEntrada, TipoPagamento tipoPagamento){
        return entradaService.finalizarEntrada(idEntrada, tipoPagamento);
    }

    @GetMapping("/atualizarEntrada")
    public Entrada updtateEntrada(Long idEntrada, EntradaRequest entradaRequest, Long idNovoQuarto){
        return entradaService.updtateEntrada(idEntrada, entradaRequest, idNovoQuarto);
    }
}
