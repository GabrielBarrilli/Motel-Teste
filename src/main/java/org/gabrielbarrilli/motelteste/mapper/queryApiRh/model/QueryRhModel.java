package org.gabrielbarrilli.motelteste.mapper.queryApiRh.model;

import org.springframework.stereotype.Component;

@Component
public class QueryRhModel {

    public String queryCodigoServidor(Integer id) {
        StringBuilder query = new StringBuilder();

        query.append(
                        """
                                SELECT
                                        rh01.rh01_cod_pessoa AS codPessoa,
                                        rh01.rh01_nome as nomePessoa,
                                        rh01.rh01_data_nascimento as dataNascimento,
                                        rh01.rh01_mae as nomeMae,
                                        rh01.rh01_pai as nomePai,
                                        rh01.rh01_atestado_fisico_mental as atestadoFisicoMental,
                                        rh01.rh01_declaracao_bens as declaracaoDeBens,
                                                          
                                        rh02.rh02_cod_servidor as codServidor,
                                                                    
                                        rh56.rh56_rg as rg,
                                        rh56.rh56_cpf as cpf,
                                        rh56.rh56_data_expedicao_rg as dataExpedicaoRg,
                                        rh56.rh56_titulo_eleitor as tituloEleitor,
                                        rh56.rh56_zona as zona,
                                        rh56.rh56_secao as secao,
                                        rh56.rh56_pis_pasep as pisPasep,
                                        rh56.rh56_certificado_reservista as certificadoReservista,
                                        rh56.rh56_serie_reservista as serieReservista,
                                        rh56.fkrh56cg02_cod_uf_reservista as idUfReservista,
                                                          
                                        rh77.rh77_cod_tipo_certificado as idTipoCertificado,
                                        rh77.rh77_descricao as descricaoTipoCertificado,
                                                          
                                        rh78.rh78_cod_categoria_reservista as idCategoriaReservista,
                                        rh78.rh78_descricao as descricaoCategoriaReservista,
                                                          
                                        cg01.cg01_cod_municipio as idMunicipio,
                                        cg01.cg01_nome as descricaoMunicipio,
                                                          
                                        cg02.cg02_cod_uf as idEstado,
                                        cg02.cg02_nome as descricaoEstado,
                                                          
                                        cg02rh01.cg02_cod_uf as idUf,
                                        cg02rh01.cg02_nome as nomeUf,
                                        cg02rh01.cg02_sigla as siglaUf,
                                                          
                                        cg02rh56reservista.cg02_cod_uf as ufExpedicao,
                                        cg02rh56reservista.cg02_nome as nomeUfReservista,
                                        cg02rh56reservista.cg02_sigla as siglaUfReservista,
                                                          
                                        cg03.cg03_cod_sexo as idSexo,
                                        cg03.cg03_descricao as descricaoSexo,
                                                          
                                        cg04rh56.cg04_cod_orgao_expedidor as idOrgaoExpeditor,
                                        cg04rh56.cg04_descricao as descricaoOrgaoExpeditor,
                                                          
                                        cg05.cg05_cod_pais as idPais,
                                        cg05.cg05_nome as descricaoPais,
                                                          
                                        cg22.cg22_cod_grupo_sanguineo as idGrupoSanguineo,
                                        cg22.cg22_descricao as descricaoGrupoSanguineo,
                                        cg22.cg22_fator_rh as fatorRH,
                                                          
                                        cg22rc.cg22_cod_raca_cor as idRacaCor,
                                        cg22rc.cg22_descricao as descricaoRacaCor,
                                                          
                                        cg23.cg23_cod_estado_civil as idEstadoCivil,
                                        cg23.cg23_descricao as descricaoEstadoCivil,
                                                          
                                        cg24.cg24_cod_grau_instrucao as idGrau,
                                        cg24.cg24_descricao as descricaoGrau,
                                                          
                                        cg29.cg29_path as pathFoto
                                                                
                                FROM rh02_servidor rh02
                                        LEFT JOIN public.rh01_pessoa rh01 on rh02.fkrh02rh01_cod_pessoa = rh01.rh01_cod_pessoa
                                        LEFT JOIN public.rh59_contato rh59 on rh01.rh01_cod_pessoa = rh59.fkrh59rh01_cod_pessoa
                                        LEFT JOIN public.rh14_servidor_lotacao rh14 on rh02.rh02_cod_servidor = rh14.fkrh14rh02_cod_servidor
                                        LEFT JOIN public.rh36_lotacao_real rh36 on rh14.fkrh14rh36_cod_lotacao_real = rh36.rh36_cod_lotacao_real
                                        LEFT JOIN public.rh33_dados_bancarios rh33 on rh01.rh01_cod_pessoa = rh33.fkrh33rh01_cod_pessoa
                                        LEFT JOIN public.cg27_banco cg27 on rh33.fkrh33cg27_cod_banco = cg27.cg27_cod_banco
                                        LEFT JOIN public.cg03_sexo cg03 on rh01.fkrh01cg03_cod_sexo = cg03.cg03_cod_sexo
                                        LEFT JOIN public.cg05_pais cg05 on rh01.fkrh01cg05_cod_pais_nac = cg05.cg05_cod_pais
                                        LEFT JOIN public.cg01_municipio cg01 on rh01.fkrh01cg01_cod_municipio_nat = cg01.cg01_cod_municipio
                                        LEFT JOIN public.cg02_uf cg02 on cg01.fkcg01cg02_cod_uf = cg02.cg02_cod_uf
                                        LEFT JOIN public.cg24_grau_instrucao cg24 on rh01.fkrh01cg24_cod_grau_instrucao = cg24.cg24_cod_grau_instrucao
                                        LEFT JOIN public.cg23_estado_civil cg23 on rh01.fkrh01cg23_cod_estado_civil = cg23.cg23_cod_estado_civil
                                        LEFT JOIN public.cg22_grupo_sanguineo cg22 on rh01.fkrh01cg22_cod_grupo_sanguineo = cg22.cg22_cod_grupo_sanguineo
                                        LEFT JOIN cg22_raca_cor cg22rc on rh01.fkrh01cg22_cod_raca_cor = cg22rc.cg22_cod_raca_cor
                                        LEFT JOIN public.rh56_documento_pessoal rh56 on rh01.rh01_cod_pessoa = rh56.fkrh56rh01_cod_pessoa
                                        LEFT JOIN public.rh77_tipo_certificado rh77 on rh56.fkrh56rh77_cod_tipo_certificado = rh77.rh77_cod_tipo_certificado
                                        LEFT JOIN public.rh78_categoria_reservista rh78 on rh56.fkrh56rh78_cod_categoria_reservista = rh78.rh78_cod_categoria_reservista
                                        LEFT JOIN public.cg29_pessoa_foto cg29 on rh01.rh01_cod_pessoa = cg29.fkcg29rh01_cod_pessoa
                                                    
                                        LEFT JOIN public.cg02_uf cg02rh56 on rh56.fkrh56cg02_cod_uf = cg02rh56.cg02_cod_uf
                                        LEFT JOIN public.cg02_uf cg02rh56reservista on rh56.fkrh56cg02_cod_uf_reservista = cg02rh56reservista.cg02_cod_uf
                                        LEFT JOIN public.cg02_uf cg02rh01 on rh01.fkrh01cg02_cod_uf_nascimento = cg02rh01.cg02_cod_uf
                                        LEFT JOIN public.cg04_orgao_expedidor cg04rh56 on rh56.fkrh56cg04_cod_orgao_expedidor = cg04rh56.cg04_cod_orgao_expedidor
                                                    
                                WHERE rh02.rh02_cod_servidor =""").append(id)
                .append("""
                                                
                        ORDER BY rh02.rh02_cod_servidor DESC LIMIT 1
                        """);
        return query.toString();
    }

    public String queryMatriculaNomeCpf(String cpf, String nome, String matricula) {
        StringBuilder query = new StringBuilder();
        query.append(
                """
                    SELECT
                        rh02.rh02_cod_servidor AS codServidor,
                        rh02.rh02_matricula    AS matricula,
                        rh01.rh01_nome         AS nome,
                        rh56.rh56_cpf          AS cpf
                                            
                    FROM rh01_pessoa rh01
                    INNER JOIN rh56_documento_pessoal rh56 ON rh01.rh01_cod_pessoa = rh56.fkrh56rh01_cod_pessoa
                    INNER JOIN rh02_servidor rh02 ON rh01.rh01_cod_pessoa = rh02.fkrh02rh01_cod_pessoa
                    INNER JOIN rh07_situacao rh07 ON rh02.fkrh02rh07_cod_situacao = rh07.rh07_cod_situacao
                                            
                    WHERE 1 = 1
                        """);

        if (cpf != null) query.append("""
                        AND rh56.rh56_cpf = '""")
                .append(cpf)
                .append("'")
                .append("""
                                                
                        ORDER BY rh02.rh02_cod_servidor DESC LIMIT 1
                        """);

        if (nome != null) query.append("""
                        AND rh01.rh01_nome = '""")
                .append(nome)
                .append("'")
                .append("""
                                                
                        ORDER BY rh02.rh02_cod_servidor DESC LIMIT 1
                        """);

        if (matricula != null) query.append("""
                        AND rh02.rh02_matricula = '""")
                .append(matricula)
                .append("'")
                .append("""
                                                
                        ORDER BY rh02.rh02_cod_servidor DESC LIMIT 1
                        """);

        return query.toString();
    }

    public String queryBuscaServidorRelatorio(Integer codServidor) {

        StringBuilder query = new StringBuilder();

        query.append("""
                SELECT
                    rh01.rh01_cod_pessoa AS codPessoa,
                    cg29.cg29_path as pathFoto,
                    rh02.rh02_cod_servidor as codServidor,
                    rh01.rh01_nome as nome,
                    rh01.rh01_data_nascimento as dataDeNascimento,
                    cg03.cg03_descricao as descricaoSexo,
                    rh01.rh01_mae as nomeMae,
                    rh01.rh01_pai as nomePai,
                    rh56.rh56_rg as rg,
                    rh56.rh56_cpf as cpf
                                
                FROM rh02_servidor rh02
                         LEFT JOIN public.rh01_pessoa rh01 on rh02.fkrh02rh01_cod_pessoa = rh01.rh01_cod_pessoa
                         LEFT JOIN public.cg03_sexo cg03 on rh01.fkrh01cg03_cod_sexo = cg03.cg03_cod_sexo
                         LEFT JOIN public.rh56_documento_pessoal rh56 on rh01.rh01_cod_pessoa = rh56.fkrh56rh01_cod_pessoa
                         LEFT JOIN public.cg29_pessoa_foto cg29 on rh01.rh01_cod_pessoa = cg29.fkcg29rh01_cod_pessoa
                                
                WHERE rh02.rh02_cod_servidor = """).append(codServidor);

        return query.toString();
    }
}
