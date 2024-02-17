package org.gabrielbarrilli.motelteste.service;

import org.gabrielbarrilli.motelteste.fixture.ItensFixture;
import org.gabrielbarrilli.motelteste.model.Itens;
import org.gabrielbarrilli.motelteste.repository.ItensRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(SpringExtension.class)
class ItensServiceTest {

    @Mock
    ItensRepository itensRepository;

    @InjectMocks
    ItensService itensService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    Itens item1 = ItensFixture.item1();
    Itens item2 = ItensFixture.item2();

    List<Itens> listaItem = ItensFixture.listaItem();

    @Test
    void findAllItens() {
        when(itensRepository.findAll()).thenReturn(listaItem);

        itensService.findAllItens();
//        List<Itens> itens = itensService.findAllItens();

//        listaItem.forEach(System.out::println);
//        itens.forEach(System.out::println);

        verify(itensRepository, atLeastOnce()).findAll();
    }


    @Test
    void createItem() {
    }
}