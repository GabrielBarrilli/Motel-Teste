package org.gabrielbarrilli.motelteste.service;

import org.gabrielbarrilli.motelteste.repository.EntradaConsumoRepository;
import org.springframework.stereotype.Service;

@Service
public class EntradaConsumoService {

    private final EntradaConsumoRepository entradaConsumoRepository;

    public EntradaConsumoService(EntradaConsumoRepository entradaConsumoRepository) {
        this.entradaConsumoRepository = entradaConsumoRepository;
    }

}
