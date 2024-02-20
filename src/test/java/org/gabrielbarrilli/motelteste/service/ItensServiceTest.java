package org.gabrielbarrilli.motelteste.service;

import org.gabrielbarrilli.motelteste.fixture.request.CriarItemRequestFixture;
import org.gabrielbarrilli.motelteste.fixture.ItensFixture;
import org.gabrielbarrilli.motelteste.model.Itens;
import org.gabrielbarrilli.motelteste.model.request.ItensRequest;
import org.gabrielbarrilli.motelteste.repository.ItensRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ItensServiceTest {

    @Mock
    ItensRepository itensRepository;

    @InjectMocks
    ItensService itensService;

    Itens item1 = ItensFixture.item1();

    List<Itens> itensList = ItensFixture.itensList();

    ItensRequest itensRequest = CriarItemRequestFixture.criarItemRequestFixture();

    @BeforeEach
    void setUp() {
    }

    @Test
    void testeFindAllItens() {

        when(itensRepository.findAll()).thenReturn(itensList);

        itensService.findAllItens();

        verify(itensRepository, atLeastOnce()).findAll();
    }

    @Test
    void testeFindAllItensEmpty() {

        List<Itens> listaVazia = new ArrayList<>();

        when(itensRepository.findAll()).thenReturn(listaVazia);

        itensService.findAllItens();

        verify(itensRepository, atLeastOnce()).findAll();
    }

    @Test
    void testeCreateItem() {

        when(itensRepository.save(any(Itens.class))).thenReturn(new Itens());

        var itemCriado = itensService.createItem(itensRequest);

        assertThat(itemCriado).isNotNull();

        verify(itensRepository, atLeastOnce()).save(any(Itens.class));
    }
}