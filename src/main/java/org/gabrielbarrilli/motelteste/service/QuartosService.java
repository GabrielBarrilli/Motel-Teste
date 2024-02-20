package org.gabrielbarrilli.motelteste.service;

import jakarta.persistence.EntityNotFoundException;
import org.gabrielbarrilli.motelteste.enums.StatusDoQuarto;
import org.gabrielbarrilli.motelteste.model.Quartos;
import org.gabrielbarrilli.motelteste.model.builders.QuartosBuilder;
import org.gabrielbarrilli.motelteste.repository.QuartosRepository;
import org.gabrielbarrilli.motelteste.model.request.RegistrarQuartoRequest;
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

    public Quartos findById(Long id) {

        return quartosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quarto não encontrado!"));
    }

    public Quartos createQuarto(RegistrarQuartoRequest registrarQuartoRequest) {
        Quartos quartos = new QuartosBuilder().
                statusDoQuarto(StatusDoQuarto.DISPONIVEL).
                descricao(registrarQuartoRequest.descricao()).
                capacidadePessoa(registrarQuartoRequest.capacidadePessoa()).
                build();
        quartosRepository.save(quartos);

        quartos.setNumero(quartos.getId());

        return quartosRepository.save(quartos);
    }
}
