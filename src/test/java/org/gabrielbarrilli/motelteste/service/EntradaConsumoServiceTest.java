package org.gabrielbarrilli.motelteste.service;

import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.gabrielbarrilli.motelteste.enums.StatusEntrada;
import org.gabrielbarrilli.motelteste.fixture.CriarConsumoRequestFixture;
import org.gabrielbarrilli.motelteste.fixture.EntradaConsumoFixture;
import org.gabrielbarrilli.motelteste.fixture.EntradaFixture;
import org.gabrielbarrilli.motelteste.fixture.ItensFixture;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.EntradaConsumo;
import org.gabrielbarrilli.motelteste.model.Itens;
import org.gabrielbarrilli.motelteste.model.request.ConsumoRequest;
import org.gabrielbarrilli.motelteste.model.response.ConsumoResponse;
import org.gabrielbarrilli.motelteste.repository.EntradaConsumoRepository;
import org.gabrielbarrilli.motelteste.repository.EntradaRepository;
import org.gabrielbarrilli.motelteste.repository.ItensRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EntradaConsumoServiceTest {

    @Mock
    EntradaRepository entradaRepository;

    @Mock
    ItensRepository itensRepository;

    @Mock
    EntradaConsumoRepository entradaConsumoRepository;

    @InjectMocks
    EntradaConsumoService entradaConsumoService;

    Entrada entradaAtiva = EntradaFixture.entradaAtiva();
    Entrada entradaFinalizada = EntradaFixture.entradaFinalizada();
    Itens itens = ItensFixture.item();

    List<EntradaConsumo> entradaConsumoList = EntradaConsumoFixture.entradaConsumoList();

    EntradaConsumo entradaConsumo1 = EntradaConsumoFixture.entradaConsumo1();

    ConsumoRequest criarConsumoRequest = CriarConsumoRequestFixture.criarConsumoRequestFixture();

    @Test
    void findAllEntradaConsumoByEntradaId() {

        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(Optional.ofNullable(entradaAtiva));
        when(entradaConsumoRepository.findAllByEntradaId(entradaAtiva.getId())).thenReturn(entradaConsumoList);

        List<ConsumoResponse> consumoResponse = entradaConsumoService.findAllEntradaConsumoByEntradaId(entradaAtiva.getId());

        consumoResponse.forEach(consumo -> assertEquals(consumo.idEntrada(), entradaAtiva.getId()));

        verify(entradaConsumoRepository, atLeastOnce()).findAllByEntradaId(entradaAtiva.getId());
    }

    @Test
    void createEntradaConsumo() {

        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(Optional.ofNullable(entradaAtiva));
        when(entradaConsumoRepository.save(any(EntradaConsumo.class))).thenReturn(new EntradaConsumo());
        when(itensRepository.findById(itens.getId())).thenReturn(Optional.ofNullable(itens));

        EntradaConsumo entradaConsumo = entradaConsumoService.createEntradaConsumo(criarConsumoRequest, entradaAtiva.getId(), itens.getId());

        assertThat(entradaConsumo).isNotNull();

        verify(entradaConsumoRepository, atLeastOnce()).save(any(EntradaConsumo.class));
    }

    @Test
    void createEntradaConsumoComEntradaFinalizada() {

        entradaAtiva.setStatusEntrada(StatusEntrada.FINALIZADA);

        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(Optional.ofNullable(entradaAtiva));
        when(entradaConsumoRepository.save(any(EntradaConsumo.class))).thenReturn(new EntradaConsumo());
        when(itensRepository.findById(itens.getId())).thenReturn(Optional.ofNullable(itens));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> entradaConsumoService.createEntradaConsumo(criarConsumoRequest, entradaAtiva.getId(), itens.getId()))
                .withMessage("Não pode adicionar um novo consumo a uma entrada já finalizada! ");
    }

    @Test
    void deleteConsumo() {

        when(entradaConsumoRepository.findById(entradaConsumo1.getId())).thenReturn(Optional.ofNullable(entradaConsumo1));

        String resposta = entradaConsumoService.deleteConsumo(entradaConsumo1.getId());

        assertEquals(resposta, "Consumo deletado!");
        verify(entradaConsumoRepository, atLeastOnce()).delete(entradaConsumo1);
    }

    @Test
    void deleteConsumoInexistente() {

        when(entradaConsumoRepository.findById(entradaConsumo1.getId())).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> entradaConsumoService.deleteConsumo(entradaConsumo1.getId()))
                .withMessage("Não foi encontrado consumo com esse id ");
    }

    @Test
    void deleteConsumoComEntradaFinalizada() {

        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(Optional.ofNullable(entradaAtiva));
        when(entradaConsumoRepository.findById(entradaConsumo1.getId())).thenReturn(Optional.ofNullable(entradaConsumo1));

        entradaConsumo1.getEntrada().setStatusEntrada(StatusEntrada.FINALIZADA);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> entradaConsumoService.deleteConsumo(entradaConsumo1.getId()))
                .withMessage("Não foi possível excluir esse consumo pois a entrada já foi finalizada! ");
    }
}