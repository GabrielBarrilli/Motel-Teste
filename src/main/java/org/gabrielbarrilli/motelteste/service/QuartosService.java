package org.gabrielbarrilli.motelteste.service;

import org.gabrielbarrilli.motelteste.Enum.StatusDoQuarto;
import org.gabrielbarrilli.motelteste.model.Quartos;
import org.gabrielbarrilli.motelteste.repository.QuartosRepository;
import org.springframework.stereotype.Service;

import java.security.PublicKey;

@Service
public class QuartosService {
    private final QuartosRepository quartosRepository;

    public QuartosService(QuartosRepository quartosRepository) {
        this.quartosRepository = quartosRepository;
    }

    public Quartos createQuarto(Quartos quartos) {
        quartos.setStatusDoQuarto(StatusDoQuarto.DISPONIVEL);
        quartosRepository.save(quartos);
        quartos.setNumero(quartos.getId());
        return quartosRepository.save(quartos);
    }
}
