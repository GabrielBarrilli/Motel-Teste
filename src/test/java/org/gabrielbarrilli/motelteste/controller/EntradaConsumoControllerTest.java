package org.gabrielbarrilli.motelteste.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gabrielbarrilli.motelteste.fixture.EntradaConsumoFixture;
import org.gabrielbarrilli.motelteste.fixture.EntradaFixture;
import org.gabrielbarrilli.motelteste.fixture.ItensFixture;
import org.gabrielbarrilli.motelteste.fixture.request.ConsumoRequestFixture;
import org.gabrielbarrilli.motelteste.fixture.response.ConsumoResponseFixture;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.EntradaConsumo;
import org.gabrielbarrilli.motelteste.model.Itens;
import org.gabrielbarrilli.motelteste.model.request.ConsumoRequest;
import org.gabrielbarrilli.motelteste.model.response.ConsumoResponse;
import org.gabrielbarrilli.motelteste.service.EntradaConsumoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = EntradaConsumoController.class)
class EntradaConsumoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EntradaConsumoService entradaConsumoService;

    @Autowired
    private ObjectMapper objectMapper;

    EntradaConsumo entradaConsumo1 = EntradaConsumoFixture.entradaConsumo1();
    List<EntradaConsumo> entradaConsumoList = EntradaConsumoFixture.entradaConsumoList();

    ConsumoRequest consumoRequest1 = ConsumoRequestFixture.consumoRequest1();

    ConsumoResponse consumoResponse1 = ConsumoResponseFixture.consumoResponse1();
    ConsumoResponse consumoResponse2 = ConsumoResponseFixture.consumoResponse2();
    List<ConsumoResponse> consumoResponseList = ConsumoResponseFixture.consumoResponseList();

    Entrada entradaAtiva = EntradaFixture.entradaAtiva();

    Itens item1 = ItensFixture.item1();

    static final String URL = "/api";

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAllEntradaConsumoByEntradaId() throws Exception {

        when(entradaConsumoService.findAllEntradaConsumoByEntradaId(anyLong())).thenReturn(consumoResponseList);

        mockMvc.perform(get(URL + "/findAllEntradaConsumoByEntradaId/" + entradaAtiva.getId()))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].id").hasJsonPath())
                .andExpect(jsonPath("$[0].total").value(consumoResponse1.total()))
                .andExpect(jsonPath("$[0].nomeItem").value(consumoResponse1.nomeItem()))
                .andExpect(jsonPath("$[0].quantidade").value(consumoResponse1.quantidade()))
                .andExpect(jsonPath("$[0].idEntrada").value(consumoResponse1.idEntrada()))
                .andExpect(jsonPath("$[0].nomeLocador").value(consumoResponse1.nomeLocador()))
                .andExpect(jsonPath("$[0].numeroQuarto").value(consumoResponse1.numeroQuarto()))
                .andExpect(jsonPath("$[0].totalEntrada").value(consumoResponse1.totalEntrada()))

                .andExpect(jsonPath("$[1].id").hasJsonPath())
                .andExpect(jsonPath("$[1].total").value(consumoResponse2.total()))
                .andExpect(jsonPath("$[1].nomeItem").value(consumoResponse2.nomeItem()))
                .andExpect(jsonPath("$[1].quantidade").value(consumoResponse2.quantidade()))
                .andExpect(jsonPath("$[1].idEntrada").value(consumoResponse2.idEntrada()))
                .andExpect(jsonPath("$[1].nomeLocador").value(consumoResponse2.nomeLocador()))
                .andExpect(jsonPath("$[1].numeroQuarto").value(consumoResponse2.numeroQuarto()))
                .andExpect(jsonPath("$[1].totalEntrada").value(consumoResponse2.totalEntrada()));
    }

    @Test
    void createEntradaConsumo() throws Exception {

        when(entradaConsumoService.createEntradaConsumo(consumoRequest1, entradaAtiva.getId(), item1.getId()))
                .thenReturn(entradaConsumo1);

        mockMvc.perform(post(URL + "/createEntradaConsumo/" + entradaAtiva.getId() + "/" + item1.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(consumoRequest1)))
                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.id").hasJsonPath())
                .andExpect(jsonPath("$.total").value(entradaConsumo1.getTotal()))
                .andExpect(jsonPath("$.itens.id").value(entradaConsumo1.getItens().getId()))
                .andExpect(jsonPath("$.quantidade").value(entradaConsumo1.getQuantidade()))
                .andExpect(jsonPath("$.entrada.id").value(entradaConsumo1.getEntrada().getId()))
                .andExpect(jsonPath("$.entrada.id").value(entradaConsumo1.getEntrada().getId()))
                .andExpect(jsonPath("$.entrada.quartos.id").value(entradaConsumo1.getEntrada().getQuartos().getId()))
                .andExpect(jsonPath("$.entrada.totalEntrada").value(entradaConsumo1.getEntrada().getTotalEntrada()));

    }

    @Test
    void deleteConsumo() throws Exception {

        when(entradaConsumoService.deleteConsumo(entradaConsumo1.getId())).thenReturn(status().isOk().toString());

        mockMvc.perform(delete(URL + "/deletarConsumo/" + entradaConsumo1.getId()))
                .andExpect(status().isOk());
    }
}