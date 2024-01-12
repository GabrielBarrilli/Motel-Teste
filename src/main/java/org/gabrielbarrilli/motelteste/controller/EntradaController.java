package org.gabrielbarrilli.motelteste.controller;

import jakarta.persistence.EntityNotFoundException;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.service.EntradaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
