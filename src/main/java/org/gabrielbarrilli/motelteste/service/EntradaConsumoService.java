package org.gabrielbarrilli.motelteste.service;

import jakarta.persistence.EntityNotFoundException;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.EntradaConsumo;
import org.gabrielbarrilli.motelteste.model.Itens;
import org.gabrielbarrilli.motelteste.repository.EntradaConsumoRepository;
import org.gabrielbarrilli.motelteste.repository.EntradaRepository;
import org.gabrielbarrilli.motelteste.repository.ItensRepository;
import org.gabrielbarrilli.motelteste.response.ConsumoRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntradaConsumoService {

    private final EntradaConsumoRepository entradaConsumoRepository;
    private final EntradaRepository entradaRepository;
    private final ItensRepository itensRepository;

    public EntradaConsumoService(EntradaConsumoRepository entradaConsumoRepository,
                                 EntradaRepository entradaRepository,
                                 ItensRepository itensRepository) {
        this.entradaConsumoRepository = entradaConsumoRepository;
        this.entradaRepository = entradaRepository;
        this.itensRepository = itensRepository;
    }

    public List<EntradaConsumo> findAllEntradaConsumo(){
        return entradaConsumoRepository.findAll();
    }

    public EntradaConsumo createEntradaConsumo(ConsumoRequest consumoRequest, Long idEntrada, Long idItem) {
        Entrada entrada = entradaRepository.findById(idEntrada).orElseThrow(()-> new EntityNotFoundException(""));

        EntradaConsumo entradaConsumo = new EntradaConsumo();

        Itens itens = itensRepository.findById(idItem).orElseThrow(()-> new RuntimeException("Item não encontrado"));

        entradaConsumo.setEntrada(entrada);
        entradaConsumo.setItens(itens);
        entradaConsumo.setQuantidade(consumoRequest.quantidade());
        entradaConsumo.setTotal(consumoRequest.quantidade() * itens.getValor());

        return entradaConsumoRepository.save(entradaConsumo);
    }


}
