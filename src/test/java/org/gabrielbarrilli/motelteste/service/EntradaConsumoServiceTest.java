package org.gabrielbarrilli.motelteste.service;

import org.gabrielbarrilli.motelteste.fixture.EntradaConsumoFixture;
import org.gabrielbarrilli.motelteste.fixture.EntradaFixture;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.EntradaConsumo;
import org.gabrielbarrilli.motelteste.model.response.ConsumoResponse;
import org.gabrielbarrilli.motelteste.repository.EntradaConsumoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EntradaConsumoServiceTest {

//    @Mock
//    private EntradaConsumoService entradaConsumoService;

    @Mock
    EntradaConsumoRepository entradaConsumoRepository;

    @InjectMocks
    EntradaConsumoService entradaConsumoService;

    Entrada entradaAtiva = EntradaFixture.entradaAtiva();

    List<EntradaConsumo> entradaConsumoList = EntradaConsumoFixture.entradaConsumoList();

    @Test
    void findAllEntradaConsumoByEntradaId() {

    }

    @Test
    void createEntradaConsumo() {
    }

    @Test
    void deleteConsumo() {
    }
}