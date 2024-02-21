package org.gabrielbarrilli.motelteste.fixture.response;

import org.gabrielbarrilli.motelteste.model.response.MapaGeralResponse;

import java.time.LocalDate;
import java.time.LocalTime;

public class MapaGeralResponseFixture {

    public static MapaGeralResponse mapaGeralResponse () {

        return new MapaGeralResponse(
                1L,
                100F,
                "ENTRADA DIA (DINHEIRO)",
                0F,
                0F,
                LocalTime.of(10,10),
                LocalDate.now()
        );
    }
}
