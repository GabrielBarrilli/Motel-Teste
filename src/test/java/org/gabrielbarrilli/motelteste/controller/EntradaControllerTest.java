package org.gabrielbarrilli.motelteste.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gabrielbarrilli.motelteste.fixture.EntradaFixture;
import org.gabrielbarrilli.motelteste.fixture.request.CriarEntradaRequestFixture;
import org.gabrielbarrilli.motelteste.fixture.response.EntradaResponseFixture;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.request.CriarEntradaRequest;
import org.gabrielbarrilli.motelteste.model.response.EntradaResponse;
import org.gabrielbarrilli.motelteste.service.EntradaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EntradaController.class)
class EntradaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EntradaService entradaService;

    @Autowired
    private ObjectMapper objectMapper;

    Entrada entradaAtiva = EntradaFixture.entradaAtiva();
    Entrada entradaFinalizada = EntradaFixture.entradaFinalizada();
    Entrada entradaParaController1 = EntradaFixture.entradaParaController1();
    Entrada entradaParaController2 = EntradaFixture.entradaParaController2();
    Entrada entradaParaCreateController = EntradaFixture.entradaParaController3();
    List<Entrada> entradaList = EntradaFixture.entradasList();

    CriarEntradaRequest entradaRequest = CriarEntradaRequestFixture.criarEntradaRequest();

    EntradaResponse entradaResponse1 = EntradaResponseFixture.entradaResponseAtiva();
    List<EntradaResponse> entradaResponseList = EntradaResponseFixture.entradaResponseList();

    static final String URL = "/api";

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllEntradas() throws Exception {

        when(entradaService.getAllEntrada()).thenReturn(entradaResponseList);

        mockMvc.perform(get(URL + "/findAllEntradas"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].id").hasJsonPath())
                .andExpect(jsonPath("$[0].nomeLocador").value(entradaParaController1.getNomeLocador()))
                .andExpect(jsonPath("$[0].placa").value(entradaParaController1.getPlaca()))
                .andExpect(jsonPath("$[0].numeroQuarto").value(entradaParaController1.getQuartos().getNumero()))
                .andExpect(jsonPath("$[0].horaEntrada").value(entradaParaController1.getHoraEntrada().toString()))
                .andExpect(jsonPath("$[0].horaSaida").value(entradaParaController1.getHoraSaida()))
                .andExpect(jsonPath("$[0].dataSaida").value(entradaParaController1.getDataSaida()))
                .andExpect(jsonPath("$[0].dataRegistroEntrada").value(entradaParaController1.getDataRegistroEntrada().toString()))
                .andExpect(jsonPath("$[0].totalEntrada").value(entradaParaController1.getTotalEntrada().toString()))
                .andExpect(jsonPath("$[0].tipoPagamento").value(entradaParaController1.getTipoPagamento().toString()))
                .andExpect(jsonPath("$[0].statusEntrada").value(entradaParaController1.getStatusEntrada().toString()))
                .andExpect(jsonPath("$[0].statusPagamento").value(entradaParaController1.getStatusPagamento().toString()))

                .andExpect(jsonPath("$[1].id").hasJsonPath())
                .andExpect(jsonPath("$[1].nomeLocador").value(entradaParaController2.getNomeLocador()))
                .andExpect(jsonPath("$[1].placa").value(entradaParaController2.getPlaca()))
                .andExpect(jsonPath("$[1].numeroQuarto").value(entradaParaController2.getQuartos().getNumero()))
                .andExpect(jsonPath("$[1].horaSaida").value(entradaParaController2.getHoraSaida().toString()))
                .andExpect(jsonPath("$[1].horaEntrada").value(entradaParaController2.getHoraEntrada().toString()))
                .andExpect(jsonPath("$[1].dataSaida").value(entradaParaController2.getDataSaida().toString()))
                .andExpect(jsonPath("$[1].dataRegistroEntrada").value(entradaParaController2.getDataRegistroEntrada().toString()))
                .andExpect(jsonPath("$[1].totalEntrada").value(entradaParaController2.getTotalEntrada().toString()))
                .andExpect(jsonPath("$[1].tipoPagamento").value(entradaParaController2.getTipoPagamento().toString()))
                .andExpect(jsonPath("$[1].statusEntrada").value(entradaParaController2.getStatusEntrada().toString()))
                .andExpect(jsonPath("$[1].statusPagamento").value(entradaParaController2.getStatusPagamento().toString()));
    }

    @Test
    void getEntradaById() throws Exception {

        when(entradaService.getEntradaById(entradaAtiva.getId())).thenReturn(entradaResponse1);

        mockMvc.perform(get(URL + "/findEntradaById/" + entradaAtiva.getId()))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").hasJsonPath())
                .andExpect(jsonPath("$.nomeLocador").value(entradaParaController1.getNomeLocador()))
                .andExpect(jsonPath("$.placa").value(entradaParaController1.getPlaca()))
                .andExpect(jsonPath("$.numeroQuarto").value(entradaParaController1.getQuartos().getNumero()))
                .andExpect(jsonPath("$.horaEntrada").value(entradaParaController1.getHoraEntrada().toString()))
                .andExpect(jsonPath("$.horaSaida").value(entradaParaController1.getHoraSaida()))
                .andExpect(jsonPath("$.dataSaida").value(entradaParaController1.getDataSaida()))
                .andExpect(jsonPath("$.dataRegistroEntrada").value(entradaParaController1.getDataRegistroEntrada().toString()))
                .andExpect(jsonPath("$.totalEntrada").value(entradaParaController1.getTotalEntrada().toString()))
                .andExpect(jsonPath("$.tipoPagamento").value(entradaParaController1.getTipoPagamento().toString()))
                .andExpect(jsonPath("$.statusEntrada").value(entradaParaController1.getStatusEntrada().toString()))
                .andExpect(jsonPath("$.statusPagamento").value(entradaParaController1.getStatusPagamento().toString()));
    }

    @Test
    void findAllByStatusEntrada() throws Exception {

        when(entradaService.findAllByStatusEntrada(entradaAtiva.getStatusEntrada())).thenReturn(entradaResponseList);

        mockMvc.perform(get(URL + "/buscaEntradasPorStatusEntrada/" + entradaAtiva.getStatusEntrada()))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].id").hasJsonPath())
                .andExpect(jsonPath("$[0].nomeLocador").value(entradaParaController1.getNomeLocador()))
                .andExpect(jsonPath("$[0].placa").value(entradaParaController1.getPlaca()))
                .andExpect(jsonPath("$[0].numeroQuarto").value(entradaParaController1.getQuartos().getNumero()))
                .andExpect(jsonPath("$[0].horaEntrada").value(entradaParaController1.getHoraEntrada().toString()))
                .andExpect(jsonPath("$[0].horaSaida").value(entradaParaController1.getHoraSaida()))
                .andExpect(jsonPath("$[0].dataSaida").value(entradaParaController1.getDataSaida()))
                .andExpect(jsonPath("$[0].dataRegistroEntrada").value(entradaParaController1.getDataRegistroEntrada().toString()))
                .andExpect(jsonPath("$[0].totalEntrada").value(entradaParaController1.getTotalEntrada().toString()))
                .andExpect(jsonPath("$[0].tipoPagamento").value(entradaParaController1.getTipoPagamento().toString()))
                .andExpect(jsonPath("$[0].statusEntrada").value(entradaParaController1.getStatusEntrada().toString()))
                .andExpect(jsonPath("$[0].statusPagamento").value(entradaParaController1.getStatusPagamento().toString()))

                .andExpect(jsonPath("$[1].id").hasJsonPath())
                .andExpect(jsonPath("$[1].nomeLocador").value(entradaParaController2.getNomeLocador()))
                .andExpect(jsonPath("$[1].placa").value(entradaParaController2.getPlaca()))
                .andExpect(jsonPath("$[1].numeroQuarto").value(entradaParaController2.getQuartos().getNumero()))
                .andExpect(jsonPath("$[1].horaSaida").value(entradaParaController2.getHoraSaida().toString()))
                .andExpect(jsonPath("$[1].horaEntrada").value(entradaParaController2.getHoraEntrada().toString()))
                .andExpect(jsonPath("$[1].dataSaida").value(entradaParaController2.getDataSaida().toString()))
                .andExpect(jsonPath("$[1].dataRegistroEntrada").value(entradaParaController2.getDataRegistroEntrada().toString()))
                .andExpect(jsonPath("$[1].totalEntrada").value(entradaParaController2.getTotalEntrada().toString()))
                .andExpect(jsonPath("$[1].tipoPagamento").value(entradaParaController2.getTipoPagamento().toString()))
                .andExpect(jsonPath("$[1].statusEntrada").value(entradaParaController2.getStatusEntrada().toString()))
                .andExpect(jsonPath("$[1].statusPagamento").value(entradaParaController2.getStatusPagamento().toString()));
    }

    @Test
    void findAllByDataDigitada() throws Exception {

        when(entradaService.findAllByDataRegistroEntrada(LocalDate.now().minusDays(1))).thenReturn(entradaResponseList);

        mockMvc.perform(get(URL + "/buscaEntradasPorDataDigitada/" + LocalDate.now().minusDays(1).toString()))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].id").hasJsonPath())
                .andExpect(jsonPath("$[0].nomeLocador").value(entradaParaController1.getNomeLocador()))
                .andExpect(jsonPath("$[0].placa").value(entradaParaController1.getPlaca()))
                .andExpect(jsonPath("$[0].numeroQuarto").value(entradaParaController1.getQuartos().getNumero()))
                .andExpect(jsonPath("$[0].horaEntrada").value(entradaParaController1.getHoraEntrada().toString()))
                .andExpect(jsonPath("$[0].horaSaida").value(entradaParaController1.getHoraSaida()))
                .andExpect(jsonPath("$[0].dataSaida").value(entradaParaController1.getDataSaida()))
                .andExpect(jsonPath("$[0].dataRegistroEntrada").value(entradaParaController1.getDataRegistroEntrada().toString()))
                .andExpect(jsonPath("$[0].totalEntrada").value(entradaParaController1.getTotalEntrada().toString()))
                .andExpect(jsonPath("$[0].tipoPagamento").value(entradaParaController1.getTipoPagamento().toString()))
                .andExpect(jsonPath("$[0].statusEntrada").value(entradaParaController1.getStatusEntrada().toString()))
                .andExpect(jsonPath("$[0].statusPagamento").value(entradaParaController1.getStatusPagamento().toString()))

                .andExpect(jsonPath("$[1].id").hasJsonPath())
                .andExpect(jsonPath("$[1].nomeLocador").value(entradaParaController2.getNomeLocador()))
                .andExpect(jsonPath("$[1].placa").value(entradaParaController2.getPlaca()))
                .andExpect(jsonPath("$[1].numeroQuarto").value(entradaParaController2.getQuartos().getNumero()))
                .andExpect(jsonPath("$[1].horaSaida").value(entradaParaController2.getHoraSaida().toString()))
                .andExpect(jsonPath("$[1].horaEntrada").value(entradaParaController2.getHoraEntrada().toString()))
                .andExpect(jsonPath("$[1].dataSaida").value(entradaParaController2.getDataSaida().toString()))
                .andExpect(jsonPath("$[1].dataRegistroEntrada").value(entradaParaController2.getDataRegistroEntrada().toString()))
                .andExpect(jsonPath("$[1].totalEntrada").value(entradaParaController2.getTotalEntrada().toString()))
                .andExpect(jsonPath("$[1].tipoPagamento").value(entradaParaController2.getTipoPagamento().toString()))
                .andExpect(jsonPath("$[1].statusEntrada").value(entradaParaController2.getStatusEntrada().toString()))
                .andExpect(jsonPath("$[1].statusPagamento").value(entradaParaController2.getStatusPagamento().toString()));

    }

    @Test
    void testFindAllByDataAtual() throws Exception {

        when(entradaService.findAllByDataRegistroEntrada(LocalDate.now())).thenReturn(emptyList());

        mockMvc.perform(get(URL + "/buscaEntradasPorDataAtual"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void createEntrada() throws Exception {

        when(entradaService.createEntrada(any(), eq(entradaParaCreateController.getQuartos().getId())))
                .thenReturn(entradaParaCreateController);

        mockMvc.perform(post(URL + "/registrarEntrada/" + entradaParaCreateController.getQuartos().getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entradaRequest)))
                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.id").hasJsonPath())
                .andExpect(jsonPath("$.nomeLocador").value(entradaParaController1.getNomeLocador()))
                .andExpect(jsonPath("$.placa").value(entradaParaController1.getPlaca()))
                .andExpect(jsonPath("$.quartos.id").value(entradaParaController1.getQuartos().getId()))
                .andExpect(jsonPath("$.quartos.numero").value(entradaParaController1.getQuartos().getNumero()))
                .andExpect(jsonPath("$.quartos.descricao").value(entradaParaController1.getQuartos().getDescricao()))
                .andExpect(jsonPath("$.quartos.capacidadePessoa").value(entradaParaController1.getQuartos().getCapacidadePessoa()))
                .andExpect(jsonPath("$.quartos.statusDoQuarto").value(entradaParaController1.getQuartos().getStatusDoQuarto().toString()))
                .andExpect(jsonPath("$.horaEntrada").value(entradaParaController1.getHoraEntrada().toString()))
                .andExpect(jsonPath("$.dataRegistroEntrada").value(entradaParaController1.getDataRegistroEntrada().toString()))
                .andExpect(jsonPath("$.totalEntrada").value(entradaParaController1.getTotalEntrada().toString()))
                .andExpect(jsonPath("$.tipoPagamento").value(entradaParaController1.getTipoPagamento().toString()))
                .andExpect(jsonPath("$.statusEntrada").value(entradaParaController1.getStatusEntrada().toString()))
                .andExpect(jsonPath("$.statusPagamento").value(entradaParaController1.getStatusPagamento().toString()));
    }

    @Test
    void updtateEntrada() throws Exception {

        when(entradaService.updateEntrada(anyLong(), any(), any(), any()))
                .thenReturn(entradaParaCreateController);

        mockMvc.perform(put(URL + "/atualizarEntrada/" + entradaParaCreateController.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(entradaRequest)))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").hasJsonPath())
                .andExpect(jsonPath("$.nomeLocador").value(entradaRequest.nomeLocador()))
                .andExpect(jsonPath("$.placa").value(entradaRequest.placa()))
                .andExpect(jsonPath("$.dataRegistroEntrada").value(entradaParaCreateController.getDataRegistroEntrada().toString()))
                .andExpect(jsonPath("$.horaEntrada").value(entradaParaCreateController.getHoraEntrada().toString()))
                .andExpect(jsonPath("$.statusEntrada").value(entradaParaCreateController.getStatusEntrada().toString()))
                .andExpect(jsonPath("$.tipoPagamento").value(entradaParaCreateController.getTipoPagamento().toString()))
                .andExpect(jsonPath("$.quartos.id").value(entradaParaCreateController.getQuartos().getId()))
                .andExpect(jsonPath("$.quartos.numero").value(entradaParaCreateController.getQuartos().getNumero()))
                .andExpect(jsonPath("$.quartos.descricao").value(entradaParaCreateController.getQuartos().getDescricao()))
                .andExpect(jsonPath("$.quartos.capacidadePessoa").value(entradaParaCreateController.getQuartos().getCapacidadePessoa()))
                .andExpect(jsonPath("$.quartos.statusDoQuarto").value(entradaParaCreateController.getQuartos().getStatusDoQuarto().toString()))
                .andExpect(jsonPath("$.statusPagamento").value(entradaParaCreateController.getStatusPagamento().toString()))
                .andExpect(jsonPath("$.totalEntrada").value(entradaParaCreateController.getTotalEntrada()));
    }
}