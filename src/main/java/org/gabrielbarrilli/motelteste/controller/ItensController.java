package org.gabrielbarrilli.motelteste.controller;

import org.gabrielbarrilli.motelteste.model.Itens;
import org.gabrielbarrilli.motelteste.request.ItensRequest;
import org.gabrielbarrilli.motelteste.service.ItensService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api")
public class ItensController {

    private final ItensService itensService;

    public ItensController(ItensService itensService) {
        this.itensService = itensService;
    }

    @GetMapping("/findAllItens")
    public List<Itens> findAllItens(){
        return itensService.findAllItens();
    }

    @PostMapping("/createItem")
    public Itens createItem(ItensRequest itensRequest){
        return itensService.createItem(itensRequest);
    }

}
