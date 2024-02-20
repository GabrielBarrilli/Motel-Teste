package org.gabrielbarrilli.motelteste.service;

import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.gabrielbarrilli.motelteste.fixture.CreateQuartoRequest;
import org.gabrielbarrilli.motelteste.fixture.QuartosFixture;
import org.gabrielbarrilli.motelteste.model.Quartos;
import org.gabrielbarrilli.motelteste.model.builders.QuartosBuilder;
import org.gabrielbarrilli.motelteste.model.request.RegistrarQuartoRequest;
import org.gabrielbarrilli.motelteste.repository.QuartosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class QuartosServiceTest {

    @Mock
    QuartosRepository quartosRepository;

    @InjectMocks
    QuartosService quartosService;

    Quartos quartoDisponivel = QuartosFixture.quartoDisponivel();
    Quartos quartoOcupado = QuartosFixture.quartoOcupado();
    List<Quartos> quartosList = QuartosFixture.quartosList();
    RegistrarQuartoRequest quartoRequest = CreateQuartoRequest.registrarQuartoRequest();

    @BeforeEach
    void setUp() {
    }

    @Test
    void testFindAll() {
        when(quartosRepository.findAll()).thenReturn(quartosList);

        quartosService.findAll();

        verify(quartosRepository, atLeastOnce()).findAll();
    }

    @Test
    void testFindById() {
        when(quartosRepository.findById(anyLong())).thenReturn(ofNullable(quartoDisponivel));

        quartosService.findById(quartoDisponivel.getId());

        verify(quartosRepository, atLeastOnce()).findById(quartoDisponivel.getId());
    }

    @Test
    void testNotFindId() {

        Assertions.assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> quartosService.findById(anyLong()))
                .withMessage("Quarto não encontrado!");
    }

    @Test
    void testCreateQuarto() {

        when(quartosRepository.save(any(Quartos.class))).thenReturn(quartoDisponivel);

        var quarto = quartosService.createQuarto(quartoRequest);

        assertThat(quarto.getId()).isNotNull();
        assertThat(quarto.getNumero()).isEqualTo(quarto.getId());
        assertThat(quarto.getDescricao()).isEqualTo(quartoRequest.descricao());
        assertThat(quarto.getCapacidadePessoa()).isEqualTo(quartoRequest.capacidadePessoa());

        verify(quartosRepository, atLeastOnce()).save(any(Quartos.class));
    }
}