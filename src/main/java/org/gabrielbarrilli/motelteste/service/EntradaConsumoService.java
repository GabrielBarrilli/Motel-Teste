package org.gabrielbarrilli.motelteste.service;

import jakarta.persistence.EntityNotFoundException;
import org.gabrielbarrilli.motelteste.Enum.StatusEntrada;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.EntradaConsumo;
import org.gabrielbarrilli.motelteste.model.Itens;
import org.gabrielbarrilli.motelteste.model.builders.EntradaConsumoBuilder;
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

    public List<EntradaConsumo> findAllEntradaConsumoByEntradaId(Long idEntrada) {
        return entradaConsumoRepository.findAllByEntradaId(idEntrada);
    }

    public EntradaConsumo createEntradaConsumo(ConsumoRequest consumoRequest, Long idEntrada, Long idItem) {
        Entrada entrada = entradaRepository.findById(idEntrada).
                orElseThrow(() -> new EntityNotFoundException("Entrada não existe "));

        Itens itens = itensRepository.findById(idItem).
                orElseThrow(() -> new RuntimeException("Item não encontrado "));

        if (entrada.getStatusEntrada() != StatusEntrada.FINALIZADA) {
            EntradaConsumo entradaConsumo = new EntradaConsumoBuilder().
                    entrada(entrada).
                    itens(itens).
                    quantidade(consumoRequest.quantidade()).

                    total(consumoRequest.quantidade() * itens.getValor()).
                    build();
            var valorT = entrada.getTotalEntrada() + entradaConsumo.getTotal();
            entrada.setTotalEntrada(valorT);

            return entradaConsumoRepository.save(entradaConsumo);
        } else {
            throw new IllegalArgumentException("Não pode adicionar um novo consumo a uma entrada já finalizada! ");
        }
    }

    public void deleteConsumo(Long idConsumo){
        EntradaConsumo entradaConsumo = entradaConsumoRepository.findById(idConsumo).
                orElseThrow(() -> new EntityNotFoundException("Não foi encontrado consumo com esse id "));

        if (entradaConsumo.getEntrada().getStatusEntrada() == StatusEntrada.FINALIZADA) {
            throw new IllegalArgumentException("Não foi possível excluir esse consumo pois a entrada já foi finalizada! ");
        }

        var total = entradaConsumo.getEntrada().getTotalEntrada() - entradaConsumo.getTotal();

        entradaConsumo.getEntrada().setTotalEntrada(total);

        entradaConsumoRepository.delete(entradaConsumo);
    }

}
