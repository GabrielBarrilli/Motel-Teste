package org.gabrielbarrilli.motelteste.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gabrielbarrilli.motelteste.fixture.MapaGeralFixture;
import org.gabrielbarrilli.motelteste.fixture.request.AlterarValorRequestFixture;
import org.gabrielbarrilli.motelteste.fixture.response.MapaGeralResponseFixture;
import org.gabrielbarrilli.motelteste.model.MapaGeral;
import org.gabrielbarrilli.motelteste.model.request.AlterarValorRequest;
import org.gabrielbarrilli.motelteste.model.response.MapaGeralResponse;
import org.gabrielbarrilli.motelteste.service.MapaGeralService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MapaGeralController.class)
class MapaGeralControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MapaGeralService mapaGeralService;

    @Autowired
    private ObjectMapper objectMapper;

    MapaGeral mapaGeral1 = MapaGeralFixture.mapaGeralDinheiroDia();
    MapaGeral mapaGeral2 = MapaGeralFixture.mapaGeralDinheiroNoite();
    List<MapaGeral> mapaGeralList = MapaGeralFixture.mapaGeralList();

    AlterarValorRequest alterarValorRequest = AlterarValorRequestFixture.alterarValorRequestPositivo();

    MapaGeralResponse mapaGeralResponse = MapaGeralResponseFixture.mapaGeralResponse();

    static final String URL = "/api";

    @BeforeEach
    void setUp() {
    }

    @Test
    void alterarValor() throws Exception {

        when(mapaGeralService.alterarValor(alterarValorRequest)).thenReturn(mapaGeral1);

        mockMvc.perform(post(URL + "/alterarValorDoCaixa")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(alterarValorRequest)))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(mapaGeral1.getId()))
                .andExpect(jsonPath("$.apartamento").value(mapaGeral1.getApartamento()))
                .andExpect(jsonPath("$.entrada").value(mapaGeral1.getEntrada()))
                .andExpect(jsonPath("$.report").value(mapaGeral1.getReport()))
                .andExpect(jsonPath("$.saida").value(mapaGeral1.getSaida()))
                .andExpect(jsonPath("$.total").value(mapaGeral1.getTotal()))
                .andExpect(jsonPath("$.hora").hasJsonPath())
                .andExpect(jsonPath("$.data").value(mapaGeral1.getData().toString()));
    }

    @Test
    void getAllMapaGeral() throws Exception{

        when(mapaGeralService.getAllMapaGeral()).thenReturn(mapaGeralList);

        mockMvc.perform(get(URL + "/findAllMapas"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].id").value(mapaGeral1.getId()))
                .andExpect(jsonPath("$[0].apartamento").value(mapaGeral1.getApartamento()))
                .andExpect(jsonPath("$[0].entrada").value(mapaGeral1.getEntrada()))
                .andExpect(jsonPath("$[0].report").value(mapaGeral1.getReport()))
                .andExpect(jsonPath("$[0].saida").value(mapaGeral1.getSaida()))
                .andExpect(jsonPath("$[0].total").value(mapaGeral1.getTotal()))
                .andExpect(jsonPath("$[0].hora").hasJsonPath())
                .andExpect(jsonPath("$[0].data").value(mapaGeral1.getData().toString()))

                .andExpect(jsonPath("$[1].id").value(mapaGeral2.getId()))
                .andExpect(jsonPath("$[1].apartamento").value(mapaGeral2.getApartamento()))
                .andExpect(jsonPath("$[1].entrada").value(mapaGeral2.getEntrada()))
                .andExpect(jsonPath("$[1].report").value(mapaGeral2.getReport()))
                .andExpect(jsonPath("$[1].saida").value(mapaGeral2.getSaida()))
                .andExpect(jsonPath("$[1].total").value(mapaGeral2.getTotal()))
                .andExpect(jsonPath("$[1].hora").hasJsonPath())
                .andExpect(jsonPath("$[1].data").value(mapaGeral2.getData().toString()));

    }

    @Test
    void findMapaGeralById() throws Exception{

        when(mapaGeralService.findMapaGeralById(mapaGeral1.getId())).thenReturn(mapaGeralResponse);

        mockMvc.perform(get(URL + "/buscaMapaPorId/" + mapaGeral1.getId()))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.id").value(mapaGeralResponse.id()))
                .andExpect(jsonPath("$.entrada").value(mapaGeralResponse.entrada()))
                .andExpect(jsonPath("$.report").value(mapaGeralResponse.report()))
                .andExpect(jsonPath("$.saida").value(mapaGeralResponse.saida()))
                .andExpect(jsonPath("$.totalCaixa").value(mapaGeralResponse.totalCaixa()))
                .andExpect(jsonPath("$.hora").hasJsonPath())
                .andExpect(jsonPath("$.data").value(mapaGeralResponse.data().toString()));

    }
}