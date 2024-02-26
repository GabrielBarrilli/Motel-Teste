package org.gabrielbarrilli.motelteste.mapper.queryApiRh.response;

public record QueryMatriculaNomeCpfResponse(
        Integer codServidor,
        String matricula,
        String nome,
        String cpf
) {
}
