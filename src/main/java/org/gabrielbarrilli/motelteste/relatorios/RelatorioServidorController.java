package org.gabrielbarrilli.motelteste.relatorios;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/relatorio")
public class RelatorioServidorController {

    private final RelatorioServidorService relatorioServidorService;

    public RelatorioServidorController(RelatorioServidorService relatorioServidorService) {
        this.relatorioServidorService = relatorioServidorService;
    }

    @GetMapping("/gerarRelatorio/{codServidor}")
    @ResponseStatus(OK)
    public void servidorRelatorio( @PathVariable Integer codServidor, HttpServletResponse response) throws Exception {

        relatorioServidorService.servidorRelatorio(codServidor, response);
    }
}