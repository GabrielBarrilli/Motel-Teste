package org.gabrielbarrilli.motelteste.fixture;

import org.gabrielbarrilli.motelteste.model.request.AlterarValorRequest;

public class AlterarValorRequestFixture {

   public static AlterarValorRequest alterarValorRequestPositivo() {
       return new AlterarValorRequest(
               1,
               100F
       );
   }

    public static AlterarValorRequest alterarValorRequestNegativo() {
        return new AlterarValorRequest(
                1,
                -100F
        );
    }
}
