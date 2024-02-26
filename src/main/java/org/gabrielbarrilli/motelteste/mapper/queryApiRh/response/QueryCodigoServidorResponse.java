package org.gabrielbarrilli.motelteste.mapper.queryApiRh.response;

import java.time.LocalDate;

public record QueryCodigoServidorResponse(
        Integer codPessoa,
        String nomePessoa,
        LocalDate dataNascimento,
        String nomePai,
        String nomeMae,
        Boolean atestadoFisicoMental,
        Boolean declaracaoDeBens,

        Integer codServidor,

        String rg,
        String cpf,
        LocalDate dataExpedicaoRg,
        String tituloEleitor,
        String zona,
        String secao,
        String pisPasep,
        String certificadoReservista,
        String serieReservista,
        Integer idUfReservista,

        Integer idTipoCertificado,
        String descricaoTipoCertificado,

        Integer idCategoriaReservista,
        String descricaoCategoriaReservista,

        Integer idMunicipio,
        String descricaoMunicipio,

        Integer idEstado,
        String descricaoEstado,

        Integer idUf,
        String nomeUf,
        String siglaUf,

        Integer ufExpedicao,
        String nomeUfReservista,
        String siglaUfReservista,

        Integer idSexo,
        String descricaoSexo,

        Integer idOrgaoExpeditor,
        String descricaoOrgaoExpeditor,

        Integer idPais,
        String descricaoPais,

        Integer idGrupoSanguineo,
        String descricaoGrupoSanguineo,
        String fatorRh,

        Integer idRacaCor,
        String descricaoRacaCor,

        Integer idEstadoCivil,
        String descricaoEstadoCivil,

        Integer idGrau,
        String descricaoGrau,

        String pathFoto
) {
}
