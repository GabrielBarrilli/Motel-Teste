package org.gabrielbarrilli.motelteste.relatorios;

import jakarta.servlet.http.HttpServletResponse;
import org.gabrielbarrilli.motelteste.mapper.queryApiRh.response.QueryServidorRelatorioResponse;
import org.gabrielbarrilli.motelteste.mapper.queryApiRh.service.QueryRhService;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RelatorioServidorService {

    private final ReportImageUtil reportImageUtil;
    private final QueryRhService queryRhService;
    private static final Map<String, Object> PARAMETROS = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(RelatorioServidorService.class);

    public RelatorioServidorService(ReportImageUtil reportImageUtil, QueryRhService queryRhService) {
        this.reportImageUtil = reportImageUtil;
        this.queryRhService = queryRhService;
    }

    public void servidorRelatorio(Integer codServidor, HttpServletResponse response) throws Exception {
        //localiza as imagens
        final String MODELO_FERIAS_RETIFICAR_JRXML = "/relatorios/cracha-servidor.jrxml";

        //nome do pdf
        String fileName = "cracha-servidor";

        BufferedImage background = reportImageUtil.getBufferedImage("imagens/fotoServidor/background.png");

        //faz a query do servidor
        var servidor = queryRhService.buscaServidorPorCodigoServidorRelatorio(codServidor);

        //cria um objeto igual a query, com os mesmos parametros, só que inserindo get e set
        ServidorRelatorio servidorRelatorio = new ServidorRelatorio();


        //cria uma lista
        List<ServidorRelatorio> servidorRelatorioList = new ArrayList<>();

        //joga o objeto dentro dalista
        servidorRelatorioList.add(servidorRelatorio);

        //pega os dados do servidor e joga no teste
        teste(servidor);

        final Map<String, Object> PARAMETROS = new HashMap<>();
        //associa os parametros com o nome das variaveis do objeto
        getParametros(PARAMETROS, background, servidorRelatorio);

        //printa pta pdf
        printPDF(MODELO_FERIAS_RETIFICAR_JRXML, servidorRelatorioList, fileName, PARAMETROS, response);
    }

    private static ServidorRelatorio teste(QueryServidorRelatorioResponse servidor) {

        ServidorRelatorio servidorRelatorio = new ServidorRelatorio();
        servidorRelatorio.setCodPessoa(servidor.codPessoa());
        servidorRelatorio.setPathFoto(servidor.pathFoto());
        servidorRelatorio.setCodServidor(servidor.codServidor());
        servidorRelatorio.setNome(servidor.nome());
        servidorRelatorio.setDataDeNascimento(servidor.dataDeNascimento());
        servidorRelatorio.setDescricaoSexo(servidor.descricaoSexo());
        servidorRelatorio.setNomeMae(servidor.nomeMae());
        servidorRelatorio.setNomePai(servidor.nomePai());
        servidorRelatorio.setCpf(servidor.cpf());
        servidorRelatorio.setRg(servidor.rg());

        return servidorRelatorio;
    }

    private void getParametros(Map<String, Object> parametros, BufferedImage background,ServidorRelatorio servidorRelatorio) {

        parametros.put("background", background);
        parametros.put("codPessoa", servidorRelatorio.getCodPessoa());
        parametros.put("codServidor", servidorRelatorio.getCodServidor());
        parametros.put("nome", servidorRelatorio.getNome());
        parametros.put("dataDeNascimento", servidorRelatorio.getDataDeNascimento());
        parametros.put("descricaoSexo", servidorRelatorio.getDescricaoSexo());
        parametros.put("nomeMae", servidorRelatorio.getNomeMae());
        parametros.put("nomePai", servidorRelatorio.getNomePai());
        parametros.put("rg", servidorRelatorio.getRg());
        parametros.put("cpf", servidorRelatorio.getCpf());
    }

    public void printPDF(String jasperPath, List<?> dataSource, String fileName, Map<String, Object> parametros, HttpServletResponse response) throws Exception {
        InputStream relativeWebPath = this.getClass().getResourceAsStream(jasperPath);
        logger.info("relativeWebPath: {}", relativeWebPath);
        logger.info("jasperPath: {}", jasperPath);
        OutputStream outputStream = response.getOutputStream();
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline; filename=" + fileName);
        var report = JasperCompileManager.compileReport(relativeWebPath);
        JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(dataSource, false);
        JasperPrint print = JasperFillManager.fillReport(report, parametros, source);
        JasperExportManager.exportReportToPdfStream(print, outputStream);
        outputStream.flush();
        outputStream.close();
    }


}
