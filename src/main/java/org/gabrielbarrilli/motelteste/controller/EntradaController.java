package org.gabrielbarrilli.motelteste.controller;

import jakarta.persistence.EntityNotFoundException;
import org.gabrielbarrilli.motelteste.Enum.StatusDoQuarto;
import org.gabrielbarrilli.motelteste.Enum.StatusEntrada;
import org.gabrielbarrilli.motelteste.Enum.StatusPagamento;
import org.gabrielbarrilli.motelteste.Enum.TipoPagamento;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.Quartos;
import org.gabrielbarrilli.motelteste.service.EntradaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    public List<Entrada> getAllEntrada() {
        return entradaService.getAllEntrada();
    }

    @GetMapping("/findEntradaById")
    public Entrada getEntradaById(Long id) {
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
    public Entrada createEntrada(Entrada entrada, @RequestBody @PathVariable Long idQuarto){
        entrada.setStatusEntrada(StatusEntrada.ATIVA);
        entrada.setTipoPagamento(TipoPagamento.PENDENTE);
        entrada.setHoraSaida(null);

        entrada.setStatusPagamento(StatusPagamento.PENDENTE);
        entrada.setTotalEntrada(null);

        return entradaService.createEntrada(entrada, idQuarto);
    }

    @GetMapping("/atualizarEntrada")
    public Entrada updtateEntrada( Long idEntrada){
        return entradaService.updtateEntrada(idEntrada);
    }
}
