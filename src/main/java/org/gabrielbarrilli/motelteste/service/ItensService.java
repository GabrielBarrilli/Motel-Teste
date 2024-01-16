package org.gabrielbarrilli.motelteste.service;

import org.gabrielbarrilli.motelteste.model.Itens;
import org.gabrielbarrilli.motelteste.repository.EntradaConsumoRepository;
import org.gabrielbarrilli.motelteste.repository.ItensRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItensService {

    private final ItensRepository itensRepository;

    public ItensService(ItensRepository itensRepository) {
        this.itensRepository = itensRepository;
    }

    public List<Itens> findAllItens(){
        return itensRepository.findAll();
    }

    public Itens createItem(Itens itens){
        return itensRepository.save(itens);
    }
}
