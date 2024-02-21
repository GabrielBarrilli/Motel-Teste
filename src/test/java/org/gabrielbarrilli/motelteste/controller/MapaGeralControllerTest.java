package org.gabrielbarrilli.motelteste.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gabrielbarrilli.motelteste.fixture.MapaGeralFixture;
import org.gabrielbarrilli.motelteste.fixture.request.AlterarValorRequestFixture;
import org.gabrielbarrilli.motelteste.model.MapaGeral;
import org.gabrielbarrilli.motelteste.model.request.AlterarValorRequest;
import org.gabrielbarrilli.motelteste.service.MapaGeralService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;


import static java.util.Collections.emptyList;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MapaGeralControllerTest.class)
class MapaGeralControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MapaGeralService mapaGeralService;

    @Autowired
    private ObjectMapper objectMapper;

    MapaGeral mapaGeral = MapaGeralFixture.mapaGeralDinheiroDia();

    AlterarValorRequest alterarValorRequest = AlterarValorRequestFixture.alterarValorRequestPositivo();


    static final String URL = "/api";

    @BeforeEach
    void setUp() {
    }

    @Test
    void alterarValor() throws Exception {

        when(mapaGeralService.alterarValor(alterarValorRequest)).thenReturn(mapaGeral);

        mockMvc.perform(post(URL + "/alterarValorDoCaixa")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(alterarValorRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").hasJsonPath());
    }

    @Test
    void getAllMapaGeral() {
    }

    @Test
    void findMapaGeralById() {
    }
}