package org.gabrielbarrilli.motelteste.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gabrielbarrilli.motelteste.fixture.EntradaFixture;
import org.gabrielbarrilli.motelteste.fixture.response.EntradaResponseFixture;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.response.EntradaResponse;
import org.gabrielbarrilli.motelteste.service.EntradaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EntradaController.class)
class EntradaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private EntradaService entradaService;

    @Autowired
    private ObjectMapper objectMapper;

    Entrada entradaAtiva = EntradaFixture.entradaAtiva();
    Entrada entradaFinalizada = EntradaFixture.entradaFinalizada();
    Entrada entradaParaController1 = EntradaFixture.entradaParaController1();
    Entrada entradaParaController2 = EntradaFixture.entradaParaController2();
    List<Entrada> entradaList = EntradaFixture.entradasList();

    EntradaResponse entradaResponse1 = EntradaResponseFixture.entradaResponseAtiva();
    List<EntradaResponse> entradaResponseList = EntradaResponseFixture.entradaResponseList();

    static final String URL = "/api";

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllEntradas() throws Exception{

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
    void testGetAllEntradas() {
    }

    @Test
    void getEntradaById() {
    }

    @Test
    void findAllByStatusEntrada() {
    }

    @Test
    void findAllByDataRegistroEntrada() {
    }

    @Test
    void testFindAllByDataRegistroEntrada() {
    }

    @Test
    void createEntrada() {
    }

    @Test
    void updtateEntrada() {
    }
}