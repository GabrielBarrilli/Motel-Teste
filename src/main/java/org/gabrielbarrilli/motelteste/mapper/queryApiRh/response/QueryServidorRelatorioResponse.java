package org.gabrielbarrilli.motelteste.mapper.queryApiRh.response;

public record QueryServidorRelatorioResponse(
        Integer codPessoa,
        String pathFoto,
        Integer codServidor,
        String nome,
        String dataDeNascimento,
        String descricaoSexo,
        String nomeMae,
        String nomePai,
        String rg,
        String cpf
) {
}
