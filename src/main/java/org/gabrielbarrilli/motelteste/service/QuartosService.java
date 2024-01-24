package org.gabrielbarrilli.motelteste.service;

import org.gabrielbarrilli.motelteste.enums.StatusDoQuarto;
import org.gabrielbarrilli.motelteste.model.Quartos;
import org.gabrielbarrilli.motelteste.repository.QuartosRepository;
import org.gabrielbarrilli.motelteste.request.RegistrarQuartoRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuartosService {
    private final QuartosRepository quartosRepository;

    public QuartosService(QuartosRepository quartosRepository) {
        this.quartosRepository = quartosRepository;
    }

    public List<Quartos> findAll(){
        return quartosRepository.findAll();
    }

    public Quartos createQuarto(RegistrarQuartoRequest registrarQuartoRequest) {
        Quartos quartos = new Quartos();
        quartos.setStatusDoQuarto(StatusDoQuarto.DISPONIVEL);
        quartos.setDescricao(registrarQuartoRequest.descricao());
        quartos.setCapacidadePessoa(registrarQuartoRequest.capacidadePessoa());
        quartosRepository.save(quartos);
        quartos.setNumero(quartos.getId());

        return quartosRepository.save(quartos);
    }
}
