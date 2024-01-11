package org.gabrielbarrilli.motelteste.service;

import org.gabrielbarrilli.motelteste.repository.EntradaConsumoRepository;
import org.gabrielbarrilli.motelteste.repository.ItensRepository;
import org.springframework.stereotype.Service;

@Service
public class ItensService {

    private final ItensRepository itensRepository;

    public ItensService(ItensRepository itensRepository) {
        this.itensRepository = itensRepository;
    }
}
