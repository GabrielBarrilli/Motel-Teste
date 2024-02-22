package org.gabrielbarrilli.motelteste.mapper.query.model;

import org.springframework.stereotype.Component;

@Component
public class QueryEntradaQuarto {
    public String queryEntradaQuarto(Long id) {
        StringBuilder query = new StringBuilder();

        query.append(
        """
                select                     
                    mt01.mt01_codigo_entrada as idEntrada,
                    mt01.mt01_nome_locador as nomeLocador,
                    mt01.mt01_placa as placa,
                    mt02.mt02_numero as numeroQuarto,
                    mt02.mt02_descricao as descricaoQuarto,
                    mt02.mt02_capacidade_pessoa as capacidadePessoa
                from mt01_entrada mt01
                left join public.mt02_quartos mt02 on mt02.mt02_codigo_quartos = mt01.fkmt01mt02_codigo_quartos
                where mt01.mt01_codigo_entrada = """).append(id);
    return query.toString();

    }

}
