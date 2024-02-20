package org.gabrielbarrilli.motelteste.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gabrielbarrilli.motelteste.fixture.request.CreateQuartoRequest;
import org.gabrielbarrilli.motelteste.fixture.QuartosFixture;
import org.gabrielbarrilli.motelteste.model.Quartos;
import org.gabrielbarrilli.motelteste.model.request.RegistrarQuartoRequest;
import org.gabrielbarrilli.motelteste.service.QuartosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = QuartosController.class)
class QuartosControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private QuartosService quartosService;

    @Autowired
    private ObjectMapper objectMapper;

    Quartos quartoDisponivel = QuartosFixture.quartoDisponivel();
    Quartos quartoOcupado = QuartosFixture.quartoOcupado();
    List<Quartos> quartosList = QuartosFixture.quartosList();

    RegistrarQuartoRequest registrarQuartoRequest = CreateQuartoRequest.registrarQuartoRequest();

    static final String URL = "/api";

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll() throws Exception {

        when(quartosService.findAll()).thenReturn(quartosList);

        mockMvc.perform(get(URL + "/findAllQuartos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").hasJsonPath())
                .andExpect(jsonPath("$[0].numero").value(quartoDisponivel.getNumero()))
                .andExpect(jsonPath("$[0].statusDoQuarto").value("DISPONIVEL"))
                .andExpect(jsonPath("$[0].capacidadePessoa").value(quartoDisponivel.getCapacidadePessoa()))
                .andExpect(jsonPath("$[0].descricao").value(quartoDisponivel.getDescricao()))
                .andExpect(jsonPath("$[1].id").hasJsonPath())
                .andExpect(jsonPath("$[1].numero").value(quartoOcupado.getNumero()))
                .andExpect(jsonPath("$[1].statusDoQuarto").value("OCUPADO"))
                .andExpect(jsonPath("$[1].capacidadePessoa").value(quartoOcupado.getCapacidadePessoa()))
                .andExpect(jsonPath("$[1].descricao").value(quartoOcupado.getDescricao()));

    }

    @Test
    void findById() throws Exception {

        when(quartosService.findById(anyLong())).thenReturn(quartoDisponivel);

        mockMvc.perform(get(URL + "/findQuartoById/" + quartoDisponivel.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists()/*hasJsonPath()*/)
                .andExpect(jsonPath("$.numero").value(quartoDisponivel.getNumero()))
                .andExpect(jsonPath("$.statusDoQuarto").value("DISPONIVEL"))
                .andExpect(jsonPath("$.capacidadePessoa").value(quartoDisponivel.getCapacidadePessoa()))
                .andExpect(jsonPath("$.descricao").value(quartoDisponivel.getDescricao()));

    }

    @Test
    void createQuarto() throws Exception {

        when(quartosService.createQuarto(registrarQuartoRequest)).thenReturn(quartoDisponivel);

        mockMvc.perform(post(URL + "/registrarQuarto")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrarQuartoRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(quartoDisponivel.getId()))
                .andExpect(jsonPath("$.numero").value(quartoDisponivel.getNumero()))
                .andExpect(jsonPath("$.descricao").value(quartoDisponivel.getDescricao()))
                .andExpect(jsonPath("$.capacidadePessoa").value(quartoDisponivel.getCapacidadePessoa()))
                .andExpect(jsonPath("$.statusDoQuarto").value(quartoDisponivel.getStatusDoQuarto().toString()));

        verify(quartosService, atLeastOnce()).createQuarto(registrarQuartoRequest);
    }

}