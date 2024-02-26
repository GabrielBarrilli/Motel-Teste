package org.gabrielbarrilli.motelteste.mapper.queryApiRh;

import org.gabrielbarrilli.motelteste.mapper.queryApiRh.response.QueryCodigoServidorResponse;
import org.gabrielbarrilli.motelteste.mapper.queryApiRh.response.QueryMatriculaNomeCpfResponse;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDate;

public class QueryMapper {
    public static RowMapper<QueryCodigoServidorResponse> rowMapperCodigoServidor =
            ((rs, rowNum) -> new QueryCodigoServidorResponse(
                    rs.getInt("codPessoa"),
                    rs.getString("nomePessoa"),
                    LocalDate.parse("dataNascimento"),
                    rs.getString("nomePai"),
                    rs.getString("nomeMae"),
                    Boolean.parseBoolean("atestadoFisicoMental"),
                    Boolean.parseBoolean("declaracaoDeBens"),
                    rs.getInt("codServidor"),
                    rs.getString("rg"),
                    rs.getString("cpf"),
                    LocalDate.parse("dataExpedicaoRg"),
                    rs.getString("tituloEleitor"),
                    rs.getString("zona"),
                    rs.getString("secao"),
                    rs.getString("pisPasep"),
                    rs.getString("certificadoReservista"),
                    rs.getString("serieReservista"),
                    rs.getInt("idUfReservista"),
                    rs.getInt("idTipoCertificado"),
                    rs.getString("descricaoTipoCertificado"),
                    rs.getInt("idCategoriaReservista"),
                    rs.getString("descricaoCategoriaReservista"),
                    rs.getInt("idMunicipio"),
                    rs.getString("descricaoMunicipio"),
                    rs.getInt("idEstado"),
                    rs.getString("descricaoEstado"),
                    rs.getInt("idUf"),
                    rs.getString("nomeUf"),
                    rs.getString("siglaUf"),
                    rs.getInt("ufExpedicao"),
                    rs.getString("nomeUfReservista"),
                    rs.getString("siglaUfReservista"),
                    rs.getInt("idSexo"),
                    rs.getString("descricaoSexo"),
                    rs.getInt("idOrgaoExpeditor"),
                    rs.getString("descricaoOrgaoExpeditor"),
                    rs.getInt("idPais"),
                    rs.getString("descricaoPais"),
                    rs.getInt("idGrupoSanguineo"),
                    rs.getString("descricaoGrupoSanguineo"),
                    rs.getString("fatorRh"),
                    rs.getInt("idRacaCor"),
                    rs.getString("descricaoRacaCor"),
                    rs.getInt("idEstadoCivil"),
                    rs.getString("descricaoEstadoCivil"),
                    rs.getInt("idGrau"),
                    rs.getString("descricaoGrau"),
                    rs.getString("pathFoto")
            ));

    public static RowMapper<QueryMatriculaNomeCpfResponse> rowMapperMatriculaNomeCpf =
            ((rs, rowNum) -> new QueryMatriculaNomeCpfResponse(
                    rs.getInt("codServidor"),
                    rs.getString("matricula"),
                    rs.getString("nome"),
                    rs.getString("cpf")
            ));
}
