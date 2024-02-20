package org.gabrielbarrilli.motelteste.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gabrielbarrilli.motelteste.fixture.ItensFixture;
import org.gabrielbarrilli.motelteste.fixture.request.ItensRequestFixture;
import org.gabrielbarrilli.motelteste.model.Itens;
import org.gabrielbarrilli.motelteste.model.request.ItensRequest;
import org.gabrielbarrilli.motelteste.service.ItensService;
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

@WebMvcTest(controllers = ItensController.class)
class ItensControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ItensService itensService;

    @Autowired
    private ObjectMapper objectMapper;

    Itens item1 = ItensFixture.item1();
    Itens item2 = ItensFixture.item2();
    List<Itens> itensList = ItensFixture.itensList();

    ItensRequest itensRequest = ItensRequestFixture.itensRequest();

    static final String URL = "/api";

    @BeforeEach
    void setUp() {
    }

    @Test
    void testFindAllItens() throws Exception {

        when(itensService.findAllItens()).thenReturn(itensList);

        mockMvc.perform(get(URL + "/findAllItens"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").hasJsonPath())
                .andExpect(jsonPath("$[0].nomeItem").value(item1.getNomeItem()))
                .andExpect(jsonPath("$[0].valor").value(item1.getValor()))
                .andExpect(jsonPath("$[1].id").hasJsonPath())
                .andExpect(jsonPath("$.[1].nomeItem").value(item2.getNomeItem()))
                .andExpect(jsonPath("$.[1].valor").value(item2.getValor()));

        verify(itensService, atLeastOnce()).findAllItens();

    }

    @Test
    void testCreateItem() throws Exception {

        when(itensService.createItem(itensRequest)).thenReturn(item1);

        mockMvc.perform(post(URL + "/createItem")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itensRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(item1.getId()))
                .andExpect(jsonPath("$.nomeItem").value(item1.getNomeItem()))
                .andExpect(jsonPath("$.valor").value(item1.getValor()));

        verify(itensService, atLeastOnce()).createItem(itensRequest);
    }
}