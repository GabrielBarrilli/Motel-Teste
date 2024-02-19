package org.gabrielbarrilli.motelteste.service;

import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.gabrielbarrilli.motelteste.enums.StatusEntrada;
import org.gabrielbarrilli.motelteste.enums.TipoPagamento;
import org.gabrielbarrilli.motelteste.fixture.AlterarValorRequestFixture;
import org.gabrielbarrilli.motelteste.fixture.EntradaFixture;
import org.gabrielbarrilli.motelteste.fixture.MapaGeralFixture;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.MapaGeral;
import org.gabrielbarrilli.motelteste.model.request.AlterarValorRequest;
import org.gabrielbarrilli.motelteste.repository.EntradaRepository;
import org.gabrielbarrilli.motelteste.repository.MapaGeralRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class MapaGeralServiceTest {

    @Mock
    MapaGeralRepository mapaGeralRepository;

    @Mock
    EntradaRepository entradaRepository;

    @InjectMocks
    MapaGeralService mapaGeralService;

    MapaGeral mapaGeralNegativo = MapaGeralFixture.mapaGeralNegativo();
    MapaGeral mapaGeralDinheiroDia = MapaGeralFixture.mapaGeralDinheiroDia();
    MapaGeral mapaGeralPixDia = MapaGeralFixture.mapaGeralPixDia();
    MapaGeral mapaGeralCartaoDia = MapaGeralFixture.mapaGeralCartaoDia();
    MapaGeral mapaGeralDinheiroNoite = MapaGeralFixture.mapaGeralDinheiroNoite();
    MapaGeral mapaGeralPixNoite = MapaGeralFixture.mapaGeralPixNoite();
    MapaGeral mapaGeralCartaoNoite = MapaGeralFixture.mapaGeralCartaoNoite();
    AlterarValorRequest alterarValorRequestPositivo = AlterarValorRequestFixture.alterarValorRequestPositivo();
    AlterarValorRequest alterarValorRequestNegativo = AlterarValorRequestFixture.alterarValorRequestNegativo();
    List<MapaGeral> mapaGeralList = MapaGeralFixture.mapaGeralList();

    Entrada entradaAtiva = EntradaFixture.entradaAtiva();

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGetAllMapaGeral() {

        when(mapaGeralRepository.findAll()).thenReturn(mapaGeralList);

        mapaGeralService.getAllMapaGeral();

        verify(mapaGeralRepository, atLeastOnce()).findAll();
    }

    @Test
    void testGetAllMapaGeralEmpty() {

        List<MapaGeral> listaVazia = new ArrayList<>();

        when(mapaGeralRepository.findAll()).thenReturn(listaVazia);

        mapaGeralService.getAllMapaGeral();

        verify(mapaGeralRepository, atLeastOnce()).findAll();
    }

    @Test
    void testFindMapaGeralById() {

        when(mapaGeralRepository.findById(anyLong())).thenReturn(ofNullable(mapaGeralDinheiroDia));

        var mapaGeralByid = mapaGeralService.findMapaGeralById(mapaGeralDinheiroDia.getId());

        verify(mapaGeralRepository, atLeastOnce()).findById(mapaGeralByid.id());
    }

    @Test
    void testFindMapaGeralByIdNotFound() {

        Assertions.assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> mapaGeralService.findMapaGeralById(mapaGeralDinheiroDia.getId()))
                .withMessage("Mapa não encontrado, digite outro id!");
    }

    @Test
    void testAlterarValorPositivo() {

        when(mapaGeralRepository.save(any(MapaGeral.class))).thenReturn(mapaGeralNegativo);

        MapaGeral mapaGeralRequest = mapaGeralService.alterarValor(alterarValorRequestPositivo);

        assertThat(mapaGeralRequest).isNotNull();

        verify(mapaGeralRepository, atLeastOnce()).save(any(MapaGeral.class));
    }

    @Test
    void testAlterarValorNegativo() {

        when(mapaGeralRepository.save(any(MapaGeral.class))).thenReturn(mapaGeralNegativo);

        MapaGeral mapaGeralRequest = mapaGeralService.alterarValor(alterarValorRequestNegativo);

        assertThat(mapaGeralRequest).isNotNull();

        verify(mapaGeralRepository, atLeastOnce()).save(any(MapaGeral.class));
    }

    @Test
    void testAlterarValorDetalhado() {

        when(mapaGeralRepository.save(any(MapaGeral.class))).thenReturn(mapaGeralDinheiroDia);

        MapaGeral mapaGeralRequest = mapaGeralService.alterarValor(alterarValorRequestPositivo);

        assertThat(mapaGeralDinheiroDia.getData()).isEqualTo(LocalDate.now());
        assertThat(mapaGeralDinheiroDia.getHora()).isEqualTo(LocalTime.of(10, 10));
        assertThat(mapaGeralDinheiroDia.getTotal()).isEqualTo(0F);
        assertThat(mapaGeralDinheiroDia.getSaida()).isEqualTo(0F);
        assertThat(mapaGeralDinheiroDia.getEntrada()).isEqualTo(100F);

        assertThat(mapaGeralDinheiroDia.getApartamento()).isEqualTo(mapaGeralRequest.getApartamento());

        assertThat(mapaGeralDinheiroDia.getReport()).isEqualTo("ENTRADA DIA (DINHEIRO)");

    }

    @Test
    void testCriarMapaTotalMapNull() {

        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(ofNullable(entradaAtiva));
        when(mapaGeralRepository.calculaTotal()).thenReturn(null);
        when(mapaGeralRepository.save(any(MapaGeral.class))).thenReturn(mapaGeralDinheiroDia);

        entradaAtiva.setStatusEntrada(StatusEntrada.FINALIZADA);
        entradaAtiva.setTipoPagamento(TipoPagamento.DINHEIRO);
        entradaAtiva.setTotalEntrada(100F);

        mapaGeralService.criarMapa(entradaAtiva.getId());

        verify(mapaGeralRepository, atLeastOnce()).save(any(MapaGeral.class));

        ArgumentCaptor<MapaGeral> mapaGeralCaptor = ArgumentCaptor.forClass(MapaGeral.class);
        verify(mapaGeralRepository).save(mapaGeralCaptor.capture());

        MapaGeral mapaGeralCapturado = mapaGeralCaptor.getValue();
        // Adicione verificações específicas para os valores do mapaGeralCapturado, por exemplo:
        assertEquals(LocalDate.now(), mapaGeralDinheiroDia.getData());
        assertEquals("ENTRADA DIA (DINHEIRO)", mapaGeralDinheiroDia.getReport());
        assertEquals(100F, mapaGeralDinheiroDia.getEntrada());
    }

    @Test
    void testCriarMapaDinheiroDia() {

        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(ofNullable(entradaAtiva));
        when(mapaGeralRepository.calculaTotal()).thenReturn(100F);
        when(mapaGeralRepository.save(any(MapaGeral.class))).thenReturn(mapaGeralDinheiroDia);

        entradaAtiva.setStatusEntrada(StatusEntrada.FINALIZADA);
        entradaAtiva.setTipoPagamento(TipoPagamento.DINHEIRO);
        entradaAtiva.setTotalEntrada(100F);

        mapaGeralService.criarMapa(entradaAtiva.getId());

        verify(mapaGeralRepository, atLeastOnce()).save(any(MapaGeral.class));

        ArgumentCaptor<MapaGeral> mapaGeralCaptor = ArgumentCaptor.forClass(MapaGeral.class);
        verify(mapaGeralRepository).save(mapaGeralCaptor.capture());

        MapaGeral mapaGeralCapturado = mapaGeralCaptor.getValue();
        // Adicione verificações específicas para os valores do mapaGeralCapturado, por exemplo:
        assertEquals(LocalDate.now(), mapaGeralDinheiroDia.getData());
        assertEquals("ENTRADA DIA (DINHEIRO)", mapaGeralDinheiroDia.getReport());
        assertEquals(100F, mapaGeralDinheiroDia.getEntrada());
    }

    @Test
    void testCriarMapaPixDia() {

        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(ofNullable(entradaAtiva));
        when(mapaGeralRepository.calculaTotal()).thenReturn(100F);
        when(mapaGeralRepository.save(any(MapaGeral.class))).thenReturn(mapaGeralPixDia);

        entradaAtiva.setStatusEntrada(StatusEntrada.FINALIZADA);
        entradaAtiva.setTipoPagamento(TipoPagamento.PIX);
        entradaAtiva.setTotalEntrada(100F);

        mapaGeralService.criarMapa(entradaAtiva.getId());

        verify(mapaGeralRepository, atLeastOnce()).save(any(MapaGeral.class));

        ArgumentCaptor<MapaGeral> mapaGeralCaptor = ArgumentCaptor.forClass(MapaGeral.class);
        verify(mapaGeralRepository).save(mapaGeralCaptor.capture());

        MapaGeral mapaGeralCapturado = mapaGeralCaptor.getValue();
        // Adicione verificações específicas para os valores do mapaGeralCapturado, por exemplo:
        assertEquals(LocalDate.now(), mapaGeralPixDia.getData());
        assertEquals("ENTRADA DIA (PIX)", mapaGeralPixDia.getReport());
        assertEquals(100F, mapaGeralPixDia.getEntrada());
    }

    @Test
    void testCriarMapaCartaoDia() {

        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(ofNullable(entradaAtiva));
        when(mapaGeralRepository.calculaTotal()).thenReturn(100F);
        when(mapaGeralRepository.save(any(MapaGeral.class))).thenReturn(mapaGeralCartaoDia);

        entradaAtiva.setStatusEntrada(StatusEntrada.FINALIZADA);
        entradaAtiva.setTipoPagamento(TipoPagamento.CARTAO);
        entradaAtiva.setTotalEntrada(100F);

        mapaGeralService.criarMapa(entradaAtiva.getId());

        verify(mapaGeralRepository, atLeastOnce()).save(any(MapaGeral.class));

        ArgumentCaptor<MapaGeral> mapaGeralCaptor = ArgumentCaptor.forClass(MapaGeral.class);
        verify(mapaGeralRepository).save(mapaGeralCaptor.capture());

        MapaGeral mapaGeralCapturado = mapaGeralCaptor.getValue();
        // Adicione verificações específicas para os valores do mapaGeralCapturado, por exemplo:
        assertEquals(LocalDate.now(), mapaGeralCartaoDia.getData());
        assertEquals("ENTRADA DIA (CARTAO)", mapaGeralCartaoDia.getReport());
        assertEquals(100F, mapaGeralCartaoDia.getEntrada());
    }

    @Test
    void testCriarMapaDinheiroNoite() {

        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(ofNullable(entradaAtiva));
        when(mapaGeralRepository.calculaTotal()).thenReturn(100F);
        when(mapaGeralRepository.save(any(MapaGeral.class))).thenReturn(mapaGeralDinheiroNoite);

        entradaAtiva.setStatusEntrada(StatusEntrada.FINALIZADA);
        entradaAtiva.setTipoPagamento(TipoPagamento.DINHEIRO);
        entradaAtiva.setTotalEntrada(100F);

        mapaGeralService.criarMapa(entradaAtiva.getId());

        verify(mapaGeralRepository, atLeastOnce()).save(any(MapaGeral.class));

        ArgumentCaptor<MapaGeral> mapaGeralCaptor = ArgumentCaptor.forClass(MapaGeral.class);
        verify(mapaGeralRepository).save(mapaGeralCaptor.capture());

        MapaGeral mapaGeralCapturado = mapaGeralCaptor.getValue();
        // Adicione verificações específicas para os valores do mapaGeralCapturado, por exemplo:
        assertEquals(LocalDate.now(), mapaGeralDinheiroNoite.getData());
        assertEquals(LocalTime.of(20, 20) , mapaGeralDinheiroNoite.getHora());
        assertEquals("ENTRADA NOITE (DINHEIRO)", mapaGeralDinheiroNoite.getReport());
        assertEquals(100F, mapaGeralDinheiroNoite.getEntrada());
    }

    @Test
    void testCriarMapaPixNoite() {

        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(ofNullable(entradaAtiva));
        when(mapaGeralRepository.calculaTotal()).thenReturn(100F);
        when(mapaGeralRepository.save(any(MapaGeral.class))).thenReturn(mapaGeralPixNoite);

        entradaAtiva.setStatusEntrada(StatusEntrada.FINALIZADA);
        entradaAtiva.setTipoPagamento(TipoPagamento.PIX);
        entradaAtiva.setTotalEntrada(100F);

        mapaGeralService.criarMapa(entradaAtiva.getId());

        verify(mapaGeralRepository, atLeastOnce()).save(any(MapaGeral.class));

        ArgumentCaptor<MapaGeral> mapaGeralCaptor = ArgumentCaptor.forClass(MapaGeral.class);
        verify(mapaGeralRepository).save(mapaGeralCaptor.capture());

        MapaGeral mapaGeralCapturado = mapaGeralCaptor.getValue();
        // Adicione verificações específicas para os valores do mapaGeralCapturado, por exemplo:
        assertEquals(LocalDate.now(), mapaGeralPixNoite.getData());
        assertEquals("ENTRADA NOITE (PIX)", mapaGeralPixNoite.getReport());
        assertEquals(100F, mapaGeralPixNoite.getEntrada());
    }

    @Test
    void testCriarMapaCartaoNoite() {

        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(ofNullable(entradaAtiva));
        when(mapaGeralRepository.calculaTotal()).thenReturn(100F);
        when(mapaGeralRepository.save(any(MapaGeral.class))).thenReturn(mapaGeralCartaoNoite);

        entradaAtiva.setStatusEntrada(StatusEntrada.FINALIZADA);
        entradaAtiva.setTipoPagamento(TipoPagamento.CARTAO);
        entradaAtiva.setTotalEntrada(100F);

        mapaGeralService.criarMapa(entradaAtiva.getId());

        verify(mapaGeralRepository, atLeastOnce()).save(any(MapaGeral.class));

        ArgumentCaptor<MapaGeral> mapaGeralCaptor = ArgumentCaptor.forClass(MapaGeral.class);
        verify(mapaGeralRepository).save(mapaGeralCaptor.capture());

        MapaGeral mapaGeralCapturado = mapaGeralCaptor.getValue();
        // Adicione verificações específicas para os valores do mapaGeralCapturado, por exemplo:
        assertEquals(LocalDate.now(), mapaGeralCartaoNoite.getData());
        assertEquals("ENTRADA NOITE (CARTAO)", mapaGeralCartaoNoite.getReport());
        assertEquals(100F, mapaGeralCartaoNoite.getEntrada());
    }
}





























