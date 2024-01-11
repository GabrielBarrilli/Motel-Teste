package org.gabrielbarrilli.motelteste.service;

import org.gabrielbarrilli.motelteste.repository.QuartosRepository;
import org.springframework.stereotype.Service;

@Service
public class QuartosService {
    private final QuartosRepository quartosRepository;

    public QuartosService(QuartosRepository quartosRepository) {
        this.quartosRepository = quartosRepository;
    }
}
