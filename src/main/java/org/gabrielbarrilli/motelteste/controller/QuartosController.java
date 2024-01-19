package org.gabrielbarrilli.motelteste.controller;

import org.gabrielbarrilli.motelteste.model.Quartos;
import org.gabrielbarrilli.motelteste.request.RegistrarQuartoRequest;
import org.gabrielbarrilli.motelteste.service.QuartosService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api")
public class QuartosController {
    private final QuartosService quartosService;

    public QuartosController(QuartosService quartosService) {
        this.quartosService = quartosService;
    }

    @GetMapping("/findAllQuartos")
    public List<Quartos> findAll(){
        return quartosService.findAll();
    }

    @PostMapping("/registrarQuarto")
    public Quartos createQuarto(RegistrarQuartoRequest registrarQuartoRequest) {
        return quartosService.createQuarto(registrarQuartoRequest);
    }

}
