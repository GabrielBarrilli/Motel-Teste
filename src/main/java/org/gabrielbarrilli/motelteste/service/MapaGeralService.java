package org.gabrielbarrilli.motelteste.service;

import org.gabrielbarrilli.motelteste.repository.EntradaConsumoRepository;
import org.gabrielbarrilli.motelteste.repository.MapaGeralRepository;
import org.springframework.stereotype.Service;

@Service
public class MapaGeralService {

    private final MapaGeralRepository mapaGeralRepository;

    public MapaGeralService(MapaGeralRepository mapaGeralRepository) {
        this.mapaGeralRepository = mapaGeralRepository;
    }
}
