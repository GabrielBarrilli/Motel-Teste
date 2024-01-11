package org.gabrielbarrilli.motelteste.service;

import org.gabrielbarrilli.motelteste.repository.EntradaConsumoRepository;
import org.gabrielbarrilli.motelteste.repository.TrocoRepository;
import org.springframework.stereotype.Service;

@Service
public class TrocoService {

    private final TrocoRepository trocoRepository;

    public TrocoService(TrocoRepository trocoRepository) {
        this.trocoRepository = trocoRepository;
    }
}
