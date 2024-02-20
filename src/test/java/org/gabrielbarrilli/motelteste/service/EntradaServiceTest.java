package org.gabrielbarrilli.motelteste.service;

import jakarta.persistence.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.gabrielbarrilli.motelteste.enums.StatusDoQuarto;
import org.gabrielbarrilli.motelteste.enums.StatusEntrada;
import org.gabrielbarrilli.motelteste.enums.StatusPagamento;
import org.gabrielbarrilli.motelteste.enums.TipoPagamento;
import org.gabrielbarrilli.motelteste.fixture.request.CriarEntradaRequestFixture;
import org.gabrielbarrilli.motelteste.fixture.EntradaFixture;
import org.gabrielbarrilli.motelteste.fixture.request.EntradaRequestFixture;
import org.gabrielbarrilli.motelteste.fixture.QuartosFixture;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.Quartos;
import org.gabrielbarrilli.motelteste.model.request.CriarEntradaRequest;
import org.gabrielbarrilli.motelteste.repository.EntradaRepository;
import org.gabrielbarrilli.motelteste.repository.MapaGeralRepository;
import org.gabrielbarrilli.motelteste.repository.QuartosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class EntradaServiceTest {

    @Mock
    EntradaRepository entradaRepository;

    @Mock
    QuartosRepository quartosRepository;

    @Mock
    MapaGeralRepository mapaGeralRepository;

    @InjectMocks
    EntradaService entradaService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    List<Entrada> entradasList = EntradaFixture.entradasList();
    Entrada entradaAtiva = EntradaFixture.entradaAtiva();
    Entrada entradaFinalizada = EntradaFixture.entradaFinalizada();
    Entrada entradaAtivaDiferente = EntradaFixture.entradaAtivaDiferente();
    Quartos quarto1 = QuartosFixture.quartoDisponivel();
    Quartos quarto2 = QuartosFixture.quartoOcupado();
    CriarEntradaRequest criarEntradaRequest = CriarEntradaRequestFixture.criarEntradaRequest();
    CriarEntradaRequest entradaRequestQuarto1 = EntradaRequestFixture.entradaRequestQuarto1();
    CriarEntradaRequest entradaRequestQuarto2 = EntradaRequestFixture.entradaRequestQuarto2();

    @Test
    void testGetAllEntrada() {

        when(entradaRepository.findAll()).thenReturn(entradasList);

        entradaService.getAllEntrada();

        verify(entradaRepository, atLeastOnce()).findAll();
    }

    @Test
    void testGetAllEntradaEmpty() {

        List<Entrada> listaVazia = new ArrayList<>();

        when(entradaRepository.findAll()).thenReturn(listaVazia);

        entradaService.getAllEntrada();

        verify(entradaRepository, atLeastOnce()).findAll();
    }

    @Test
    void testGetEntradaById() {

        when(entradaRepository.findById(anyLong())).thenReturn(ofNullable(entradaAtiva));

        entradaService.getEntradaById(anyLong());

        verify(entradaRepository, atLeastOnce()).findById(anyLong());
    }

    @Test
    void testFindAllByStatusEntrada() {

        when(entradaRepository.findAllByStatusEntrada(StatusEntrada.ATIVA)).thenReturn(entradasList);

        entradaService.findAllByStatusEntrada(StatusEntrada.ATIVA);

        verify(entradaRepository, atLeastOnce()).findAllByStatusEntrada(StatusEntrada.ATIVA);
    }

    @Test
    void testFindAllByDataRegistroEntrada() {

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
    void testObterEntradaPorIdInexistente() {
        Assertions.assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> entradaService.getEntradaById(entradaAtiva.getId()))
                .withMessage("Entrada não encontrada");
    }

    @Test
    void testCriarEntradaComQuartoInexistente () {
        when(quartosRepository.findById(quarto1.getId())).thenReturn(empty());

        Assertions.assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> entradaService.createEntrada(criarEntradaRequest, quarto1.getId()))
                .withMessage("Não há quarto com essa numeração");
    }

    @Test
    void testCriarEntradaComQuartoOcupado () {

        quarto1.setStatusDoQuarto(StatusDoQuarto.OCUPADO);
        when(quartosRepository.findById(quarto1.getId())).thenReturn(of(quarto1));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> entradaService.createEntrada(criarEntradaRequest, quarto1.getId()))
                .withMessage("O quarto está indisponível");
    }

    @Test
    void testCriarEntrada() {

        when(quartosRepository.findById(quarto1.getId())).thenReturn(ofNullable(quarto1));
        when(entradaRepository.save(any(Entrada.class))).thenReturn(new Entrada());

        Entrada entrada = entradaService.createEntrada(criarEntradaRequest, quarto1.getId());

        assertThat(entrada).isNotNull();
        assertThat(quarto1.getStatusDoQuarto()).isEqualTo(StatusDoQuarto.OCUPADO);

        verify(entradaRepository, atLeastOnce()).save(any(Entrada.class));
    }

    @Test
    void testCriarEntradaVerificarDetalhes() {

        when(quartosRepository.findById(quarto1.getId())).thenReturn(of(quarto1));
        when(entradaRepository.save(any(Entrada.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Entrada entrada = entradaService.createEntrada(criarEntradaRequest, quarto1.getId());

        assertThat(entrada.getPlaca()).isEqualTo(entradaRequestQuarto1.placa());
        assertThat(entrada.getQuartos()).isEqualTo(quarto1);
        assertThat(entrada.getDataRegistroEntrada()).isToday();
        assertThat(entrada.getHoraEntrada()).isNotNull();
        assertThat(entrada.getStatusEntrada()).isEqualTo(StatusEntrada.ATIVA);
        assertThat(entrada.getStatusPagamento()).isEqualTo(StatusPagamento.PENDENTE);
        assertThat(entrada.getTotalEntrada()).isEqualTo(30F);
    }

    @Test
    void testUpdateEntradaComQuartoOcupado () {

        StatusEntrada statusEntrada = StatusEntrada.ATIVA;
        TipoPagamento tipoPagamento = TipoPagamento.PENDENTE;

        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(ofNullable(entradaAtiva));
        when(entradaRepository.save(any(Entrada.class))).thenReturn(entradaAtiva);
        when(quartosRepository.findById(entradaRequestQuarto1.idQuarto())).thenReturn(ofNullable(quarto1));
        when(quartosRepository.findById(entradaRequestQuarto2.idQuarto())).thenReturn(ofNullable(quarto2));

        quarto2.setStatusDoQuarto(StatusDoQuarto.OCUPADO);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> entradaService.updateEntrada(entradaAtiva.getId(), entradaRequestQuarto2, statusEntrada, tipoPagamento))
                .withMessage("O quarto está ocupado!");
    }

    @Test
    void testUpdateEntradaComQuartoNecessitaLimpeza () {

        StatusEntrada statusEntrada = StatusEntrada.ATIVA;
        TipoPagamento tipoPagamento = TipoPagamento.PENDENTE;

        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(ofNullable(entradaAtiva));
        when(entradaRepository.save(any(Entrada.class))).thenReturn(entradaAtiva);
        when(quartosRepository.findById(entradaRequestQuarto1.idQuarto())).thenReturn(ofNullable(quarto1));
        when(quartosRepository.findById(entradaRequestQuarto2.idQuarto())).thenReturn(ofNullable(quarto2));

        quarto2.setStatusDoQuarto(StatusDoQuarto.NECESSITA_LIMPEZA);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> entradaService.updateEntrada(entradaAtiva.getId(), entradaRequestQuarto2, statusEntrada, tipoPagamento))
                .withMessage("O quarto necessita limpeza!");
    }

    @Test
    void testUpdateEntradaComQuartoReservado () {

        StatusEntrada statusEntrada = StatusEntrada.ATIVA;
        TipoPagamento tipoPagamento = TipoPagamento.PENDENTE;

        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(ofNullable(entradaAtiva));
        when(entradaRepository.save(any(Entrada.class))).thenReturn(entradaAtiva);
        when(quartosRepository.findById(entradaRequestQuarto1.idQuarto())).thenReturn(ofNullable(quarto1));
        when(quartosRepository.findById(entradaRequestQuarto2.idQuarto())).thenReturn(ofNullable(quarto2));

        quarto2.setStatusDoQuarto(StatusDoQuarto.RESERVADO);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> entradaService.updateEntrada(entradaAtiva.getId(), entradaRequestQuarto2, statusEntrada, tipoPagamento))
                .withMessage("O quarto está reservado!");
    }

    @Test
    void testUpdateEntradaComQuartoIgualNaoFinalizada() {

        StatusEntrada statusEntrada = StatusEntrada.ATIVA;
        TipoPagamento tipoPagamento = TipoPagamento.PENDENTE;

        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(ofNullable(entradaAtiva));
        when(entradaRepository.save(any(Entrada.class))).thenReturn(entradaAtiva);
        when(quartosRepository.findById(entradaRequestQuarto1.idQuarto())).thenReturn(ofNullable(quarto1));

        Entrada entradaAtualizada = entradaService.updateEntrada(entradaAtiva.getId(), entradaRequestQuarto1, statusEntrada, tipoPagamento);

        assertThat(entradaAtualizada.getStatusEntrada()).isEqualTo(statusEntrada);
        assertThat(entradaAtualizada.getPlaca()).isEqualTo(entradaAtivaDiferente.getPlaca());
    }

    @Test
    void testUpdateEntradaJaFinalizada() {

        StatusEntrada statusEntrada = StatusEntrada.FINALIZADA;
        TipoPagamento tipoPagamento = TipoPagamento.PENDENTE;

        when(entradaRepository.findById(entradaFinalizada.getId())).thenReturn(ofNullable(entradaFinalizada));
        when(entradaRepository.save(any(Entrada.class))).thenReturn(entradaFinalizada);
        when(quartosRepository.findById(entradaRequestQuarto1.idQuarto())).thenReturn(ofNullable(quarto1));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> entradaService.updateEntrada(entradaFinalizada.getId(), entradaRequestQuarto1, statusEntrada ,tipoPagamento))
                .withMessage("A entrada já está finalizada");
    }

    @Test
    void testUpdateEntradaComQuartoDifenteNaoFinalizada() {

        StatusEntrada statusEntrada = StatusEntrada.ATIVA;
        TipoPagamento tipoPagamento = TipoPagamento.PENDENTE;

        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(ofNullable(entradaAtiva));
        when(entradaRepository.save(any(Entrada.class))).thenReturn(entradaAtiva);
        when(quartosRepository.findById(entradaRequestQuarto2.idQuarto())).thenReturn(ofNullable(quarto1));

        Entrada entradaAtualizada = entradaService.updateEntrada(entradaAtiva.getId(), entradaRequestQuarto2, statusEntrada, tipoPagamento);

        assertThat(entradaAtualizada.getStatusEntrada()).isEqualTo(statusEntrada);
        assertThat(entradaAtualizada.getPlaca()).isEqualTo(entradaAtivaDiferente.getPlaca());
    }

    @Test
    void testUpdateEntradaComQuartoIgualFinalizada() {

        StatusEntrada statusEntrada = StatusEntrada.FINALIZADA;
        TipoPagamento tipoPagamento = TipoPagamento.DINHEIRO;

        var valorEntrada = 250F;
        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(ofNullable(entradaAtiva));
        when(entradaRepository.save(any(Entrada.class))).thenReturn(entradaAtiva);
        when(quartosRepository.findById(entradaRequestQuarto1.idQuarto())).thenReturn(ofNullable(quarto1));
        when(mapaGeralRepository.calculaTotal()).thenReturn(valorEntrada);

        Entrada entradaAtualizada = entradaService.updateEntrada(entradaAtiva.getId(), entradaRequestQuarto1, statusEntrada, tipoPagamento);

        assertThat(entradaAtualizada.getStatusEntrada()).isEqualTo(statusEntrada);
        assertThat(entradaAtualizada.getPlaca()).isEqualTo(entradaAtivaDiferente.getPlaca());
    }

    @Test
    void testUpdateEntradaComQuartoIgualFinalizadaHorario() {

        StatusEntrada statusEntrada = StatusEntrada.FINALIZADA;
        TipoPagamento tipoPagamento = TipoPagamento.DINHEIRO;

        var valorEntrada = 250F;
        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(ofNullable(entradaAtiva));
        when(entradaRepository.save(any(Entrada.class))).thenReturn(entradaAtiva);
        when(quartosRepository.findById(entradaRequestQuarto1.idQuarto())).thenReturn(ofNullable(quarto1));
        when(mapaGeralRepository.calculaTotal()).thenReturn(valorEntrada);

        entradaAtiva.setDataSaida(LocalDate.now().plusDays(1));

        Entrada entradaAtualizada = entradaService.updateEntrada(entradaAtiva.getId(), entradaRequestQuarto1, statusEntrada, tipoPagamento);

        assertThat(entradaAtualizada.getStatusEntrada()).isEqualTo(statusEntrada);
        assertThat(entradaAtualizada.getPlaca()).isEqualTo(entradaAtivaDiferente.getPlaca());
    }

    @Test
    void testUpdateEntradaComQuartoDiferenteFinalizada() {

        StatusEntrada statusEntrada = StatusEntrada.FINALIZADA;
        TipoPagamento tipoPagamento = TipoPagamento.DINHEIRO;

        var valorEntrada = 250F;
        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(ofNullable(entradaAtiva));
        when(entradaRepository.save(any(Entrada.class))).thenReturn(entradaAtiva);
        when(quartosRepository.findById(entradaRequestQuarto2.idQuarto())).thenReturn(ofNullable(quarto1));
        when(mapaGeralRepository.calculaTotal()).thenReturn(valorEntrada);

        Entrada entradaAtualizada = entradaService.updateEntrada(entradaAtiva.getId(), entradaRequestQuarto2, statusEntrada, tipoPagamento);

        assertThat(entradaAtualizada.getStatusEntrada()).isEqualTo(statusEntrada);
        assertThat(entradaAtualizada.getPlaca()).isEqualTo(entradaAtivaDiferente.getPlaca());
    }

    @Test
    void testUpdateEntradaComQuartoIgualEPagamentoPix() {

        StatusEntrada statusEntrada = StatusEntrada.FINALIZADA;
        TipoPagamento tipoPagamento = TipoPagamento.PIX;

        var valorEntrada = 250F;
        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(ofNullable(entradaAtiva));
        when(entradaRepository.save(any(Entrada.class))).thenReturn(entradaAtiva);
        when(quartosRepository.findById(entradaRequestQuarto1.idQuarto())).thenReturn(ofNullable(quarto1));
        when(mapaGeralRepository.calculaTotal()).thenReturn(valorEntrada);

        Entrada entradaAtualizada = entradaService.updateEntrada(entradaAtiva.getId(), entradaRequestQuarto1, statusEntrada, tipoPagamento);

        assertThat(entradaAtualizada.getStatusEntrada()).isEqualTo(statusEntrada);
        assertThat(entradaAtualizada.getPlaca()).isEqualTo(entradaAtivaDiferente.getPlaca());
    }

    @Test
    void testUpdateEntradaComQuartoIgualEPagamentoPendente() {

        StatusEntrada statusEntrada = StatusEntrada.FINALIZADA;
        TipoPagamento tipoPagamento = TipoPagamento.PENDENTE;

        var valorEntrada = 250F;
        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(ofNullable(entradaAtiva));
        when(entradaRepository.save(any(Entrada.class))).thenReturn(entradaAtiva);
        when(quartosRepository.findById(entradaRequestQuarto1.idQuarto())).thenReturn(ofNullable(quarto1));
        when(mapaGeralRepository.calculaTotal()).thenReturn(valorEntrada);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> entradaService.updateEntrada(entradaAtiva.getId(), entradaRequestQuarto1, statusEntrada ,tipoPagamento))
                .withMessage("O pagamento não pode estar pendente, selecione uma opção de pagamento!");
    }

    @Test
    void testUpdateEntradaComQuartoIgualEStatusAtivaEPagamentoPix() {

        StatusEntrada statusEntrada = StatusEntrada.ATIVA;
        TipoPagamento tipoPagamento = TipoPagamento.PIX;

        var valorEntrada = 250F;
        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(ofNullable(entradaAtiva));
        when(entradaRepository.save(any(Entrada.class))).thenReturn(entradaAtiva);
        when(quartosRepository.findById(entradaRequestQuarto1.idQuarto())).thenReturn(ofNullable(quarto1));
        when(mapaGeralRepository.calculaTotal()).thenReturn(valorEntrada);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> entradaService.updateEntrada(entradaAtiva.getId(), entradaRequestQuarto1, statusEntrada ,tipoPagamento))
                .withMessage("Não pode pagar se a entrada não for finalizada!");
    }

    @Test
    void testUpdateEntradaComQuartoIgualEStatusAtivaEPagamentoCartao() {

        StatusEntrada statusEntrada = StatusEntrada.ATIVA;
        TipoPagamento tipoPagamento = TipoPagamento.CARTAO;

        var valorEntrada = 250F;
        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(ofNullable(entradaAtiva));
        when(entradaRepository.save(any(Entrada.class))).thenReturn(entradaAtiva);
        when(quartosRepository.findById(entradaRequestQuarto1.idQuarto())).thenReturn(ofNullable(quarto1));
        when(mapaGeralRepository.calculaTotal()).thenReturn(valorEntrada);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> entradaService.updateEntrada(entradaAtiva.getId(), entradaRequestQuarto1, statusEntrada ,tipoPagamento))
                .withMessage("Não pode pagar se a entrada não for finalizada!");
    }

    @Test
    void testUpdateEntradaComQuartoIgualEStatusAtivaEPagamentoDinheiro() {

        StatusEntrada statusEntrada = StatusEntrada.ATIVA;
        TipoPagamento tipoPagamento = TipoPagamento.DINHEIRO;

        var valorEntrada = 250F;
        when(entradaRepository.findById(entradaAtiva.getId())).thenReturn(ofNullable(entradaAtiva));
        when(entradaRepository.save(any(Entrada.class))).thenReturn(entradaAtiva);
        when(quartosRepository.findById(entradaRequestQuarto1.idQuarto())).thenReturn(ofNullable(quarto1));
        when(mapaGeralRepository.calculaTotal()).thenReturn(valorEntrada);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> entradaService.updateEntrada(entradaAtiva.getId(), entradaRequestQuarto1, statusEntrada ,tipoPagamento))
                .withMessage("Não pode pagar se a entrada não for finalizada!");
    }

    @Test
    @DisplayName("Tenta atualizar uma entrada, mas a entrada não existe")
    void testUpdateEntradaEntradaInexistente() {

        Assertions.assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> entradaService.updateEntrada(entradaAtiva.getId(), entradaRequestQuarto1, StatusEntrada.ATIVA ,TipoPagamento.PENDENTE))
                .withMessage("Entrada não encontrada!");

    }

}