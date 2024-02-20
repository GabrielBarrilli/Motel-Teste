package org.gabrielbarrilli.motelteste.controller;

import org.gabrielbarrilli.motelteste.enums.StatusEntrada;
import org.gabrielbarrilli.motelteste.enums.TipoPagamento;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.request.CriarEntradaRequest;
import org.gabrielbarrilli.motelteste.model.response.EntradaResponse;
import org.gabrielbarrilli.motelteste.service.EntradaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/findAllEntradas")
    public ResponseEntity<List<EntradaResponse>> getAllEntradas() {
        List<EntradaResponse> entradas = entradaService.getAllEntrada();
        return ResponseEntity.ok(entradas);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/findEntradaById")
    public EntradaResponse getEntradaById(Long id) {
        return entradaService.getEntradaById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/buscaEntradasPorStatusEntrada/{statusEntrada}")
    public List<EntradaResponse> findAllByStatusEntrada(@PathVariable StatusEntrada statusEntrada) {
        return entradaService.findAllByStatusEntrada(statusEntrada);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/buscaEntradasPorDataAtual")
    public List<EntradaResponse> findAllByDataRegistroEntrada() {
        return entradaService.findAllByDataAtual();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/buscaEntradasPorDataDigitada/{data}")
    public List<EntradaResponse> findAllByDataRegistroEntrada(@RequestParam("data") @DateTimeFormat(pattern = "dd/MM/yyyy") @PathVariable LocalDate data) {
        return entradaService.findAllByDataRegistroEntrada(data);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registrarEntrada/{idQuarto}")
    public Entrada createEntrada(@RequestBody CriarEntradaRequest criarEntradaRequest, @PathVariable Long idQuarto) {
        return entradaService.createEntrada(criarEntradaRequest, idQuarto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/atualizarEntrada/{idEntrada}")
    public Entrada updtateEntrada(@PathVariable Long idEntrada, @RequestBody CriarEntradaRequest entradaRequest, StatusEntrada statusEntrada, TipoPagamento tipoPagamento) {
        return entradaService.updateEntrada(idEntrada, entradaRequest, statusEntrada, tipoPagamento);
    }

}
