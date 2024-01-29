package org.gabrielbarrilli.motelteste.service;

import org.gabrielbarrilli.motelteste.model.Itens;
import org.gabrielbarrilli.motelteste.model.builders.ItensBuilder;
import org.gabrielbarrilli.motelteste.repository.EntradaConsumoRepository;
import org.gabrielbarrilli.motelteste.repository.ItensRepository;
import org.gabrielbarrilli.motelteste.request.ItensRequest;
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

    public Itens createItem(ItensRequest itensRequest){

        Itens itens = new ItensBuilder().
                nomeItem(itensRequest.nomeItem()).
                valor(itensRequest.valor()).
                build();

        return itensRepository.save(itens);
    }
}
