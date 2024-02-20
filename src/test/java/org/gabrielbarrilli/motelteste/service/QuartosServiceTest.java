package org.gabrielbarrilli.motelteste.service;

import org.gabrielbarrilli.motelteste.fixture.QuartosFixture;
import org.gabrielbarrilli.motelteste.model.Quartos;
import org.gabrielbarrilli.motelteste.repository.QuartosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll() {
        when(quartosRepository.findAll()).thenReturn(quartosList);

        quartosService.findAll();

        verify(quartosRepository, atLeastOnce()).findAll();
    }

    @Test
    void createQuarto() {
    }
}