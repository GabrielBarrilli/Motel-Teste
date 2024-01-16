package org.gabrielbarrilli.motelteste.service;

import jakarta.persistence.EntityNotFoundException;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.EntradaConsumo;
import org.gabrielbarrilli.motelteste.model.Itens;
import org.gabrielbarrilli.motelteste.repository.EntradaConsumoRepository;
import org.gabrielbarrilli.motelteste.repository.EntradaRepository;
import org.gabrielbarrilli.motelteste.repository.ItensRepository;
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

    public EntradaConsumo createEntradaConsumo(EntradaConsumo entradaConsumo, Long idEntrada, Long idItem) {
        Entrada entrada = entradaRepository.findById(idEntrada).orElseThrow(()-> new EntityNotFoundException(""));

        Optional<EntradaConsumo> existingConsumo = entradaConsumoRepository
                .findByEntradaAndItens(entradaConsumo.getEntrada(), entradaConsumo.getItens());

        Itens itens = itensRepository.findById(idItem).orElseThrow(()-> new EntityNotFoundException("Item não encontrado"));

        entradaConsumo.setEntrada(entrada);

        entradaConsumo.setItens(itens);

        if (existingConsumo.isPresent()) {
            var valorTotal = existingConsumo.get().getItens().getValor() * existingConsumo.get().getQuantidade();

            existingConsumo.get().setTotal(valorTotal);

            return entradaConsumoRepository.save(existingConsumo.get());
        } else {
            var valorTotal = (entradaConsumo.getItens().getValor() * entradaConsumo.getQuantidade());
            entradaConsumo.setTotal(valorTotal);
            return entradaConsumoRepository.save(entradaConsumo);
        }
    }


}
