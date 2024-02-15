package org.gabrielbarrilli.motelteste.service;

import org.gabrielbarrilli.motelteste.enums.StatusDoQuarto;
import org.gabrielbarrilli.motelteste.enums.StatusEntrada;
import org.gabrielbarrilli.motelteste.enums.TipoPagamento;
import org.gabrielbarrilli.motelteste.fixture.CriarEntradaRequestFixture;
import org.gabrielbarrilli.motelteste.fixture.EntradaFixture;
import org.gabrielbarrilli.motelteste.fixture.EntradaRequestFixture;
import org.gabrielbarrilli.motelteste.fixture.QuartosFixture;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.Quartos;
import org.gabrielbarrilli.motelteste.model.request.CriarEntradaRequest;
import org.gabrielbarrilli.motelteste.model.request.EntradaRequest;
import org.gabrielbarrilli.motelteste.repository.EntradaRepository;
import org.gabrielbarrilli.motelteste.repository.QuartosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class EntradaServiceTest {

    @Mock
    EntradaRepository entradaRepository;

    @Mock
    QuartosRepository quartosRepository;

    @InjectMocks
    EntradaService entradaService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    List<Entrada> entradasList = EntradaFixture.entradasList();
    Entrada entradaAtiva = EntradaFixture.entradaAtiva();
    Entrada entradaAtivaDiferente = EntradaFixture.entradaAtivaDiferente();
    Quartos quartos = QuartosFixture.quarto();
    CriarEntradaRequest criarEntradaRequest = CriarEntradaRequestFixture.criarEntradaRequest();
    EntradaRequest entradaRequest = EntradaRequestFixture.entradaRequest();

    @Test
    void getAllEntrada() {

        when(entradaRepository.findAll()).thenReturn(entradasList);

        entradaService.getAllEntrada();

        verify(entradaRepository, atLeastOnce()).findAll();
    }

    @Test
    void getEntradaById() {

        when(entradaRepository.findById(anyLong())).thenReturn(Optional.ofNullable(entradaAtiva));

        entradaService.getEntradaById(anyLong());

        verify(entradaRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void findAllByStatusEntrada() {

        when(entradaRepository.findAllByStatusEntrada(StatusEntrada.ATIVA)).thenReturn(entradasList);

        entradaService.findAllByStatusEntrada(StatusEntrada.ATIVA);

        verify(entradaRepository, atLeastOnce()).findAllByStatusEntrada(StatusEntrada.ATIVA);
    }

    @Test
    void findAllByDataRegistroEntrada() {

        when(entradaRepository.findAllByDataRegistroEntrada(entradaAtiva.getDataRegistroEntrada())).thenReturn(entradasList);

        entradaService.findAllByDataRegistroEntrada(entradaAtiva.getDataRegistroEntrada());

        verify(entradaRepository, atLeastOnce()).findAllByDataRegistroEntrada(entradaAtiva.getDataRegistroEntrada());
    }

    @Test
    void testFindAllByDataAtual() {

        when(entradaRepository.findAllByDataRegistroEntrada(LocalDate.now())).thenReturn(entradasList);

        entradaService.findAllByDataAtual();

        verify(entradaRepository, atLeastOnce()).findAllByDataRegistroEntrada(LocalDate.now());
    }

    @Test
    void createEntrada() {

        when(quartosRepository.findById(quartos.getId())).thenReturn(Optional.ofNullable(quartos));
        when(entradaRepository.save(any(Entrada.class))).thenReturn(new Entrada());

        Entrada entrada = entradaService.createEntrada(criarEntradaRequest, quartos.getId());

        assertThat(entrada).isNotNull();
        assertThat(quartos.getStatusDoQuarto()).isEqualTo(StatusDoQuarto.OCUPADO);

        verify(entradaRepository, atLeastOnce()).save(any(Entrada.class));
    }

    @Test
    void updateEntrada() {

        StatusEntrada statusEntrada = StatusEntrada.FINALIZADA;
        TipoPagamento tipoPagamento = TipoPagamento.DINHEIRO;

        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(Optional.ofNullable(entradaAtiva));
        when(entradaRepository.save(any(Entrada.class))).thenReturn(entradaAtiva);

        System.out.println(entradaRequest.idQuarto());

        Entrada entradaAtualizada = entradaService.updateEntrada(entradaAtiva.getId(), entradaRequest, statusEntrada, tipoPagamento);

        assertThat(entradaAtualizada.getStatusEntrada()).isEqualTo(statusEntrada);
        assertThat(entradaAtualizada.getPlaca()).isEqualTo(entradaAtivaDiferente.getPlaca());
    }

    @Test
    void calculoTotalEntradaTempo() {
    }
}