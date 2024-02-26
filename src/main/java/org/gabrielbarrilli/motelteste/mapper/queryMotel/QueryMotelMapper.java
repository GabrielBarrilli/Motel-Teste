package org.gabrielbarrilli.motelteste.mapper.queryMotel;

import org.gabrielbarrilli.motelteste.mapper.queryMotel.response.QueryEntradaQuartoResponse;
import org.springframework.jdbc.core.RowMapper;

public class QueryMotelMapper {

    public static RowMapper<QueryEntradaQuartoResponse> rowMapperEntradaQuarto =
            ((rs, rowNum) -> new QueryEntradaQuartoResponse(
                    rs.getLong("idEntrada"),
                    rs.getString("nomeLocador"),
                    rs.getString("placa"),
                    new QueryEntradaQuartoResponse.QuartoResponse(
                            rs.getLong("numeroQuarto"),
                            rs.getString("descricaoQuarto"),
                            rs.getInt("capacidadePessoa"))

            ));
}
