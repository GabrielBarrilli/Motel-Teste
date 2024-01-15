package org.gabrielbarrilli.motelteste.service;

import jakarta.persistence.EntityNotFoundException;
import org.gabrielbarrilli.motelteste.Enum.StatusDoQuarto;
import org.gabrielbarrilli.motelteste.Enum.StatusEntrada;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.Quartos;
import org.gabrielbarrilli.motelteste.repository.EntradaConsumoRepository;
import org.gabrielbarrilli.motelteste.repository.EntradaRepository;
import org.gabrielbarrilli.motelteste.repository.QuartosRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class EntradaService {

    private final EntradaRepository entradaRepository;
    private final QuartosRepository quartosRepository;

    public EntradaService(EntradaRepository entradaRepository, QuartosRepository quartoRepository) {
        this.entradaRepository = entradaRepository;
        this.quartosRepository = quartoRepository;
    }

    public List<Entrada> getAllEntrada() {
        return entradaRepository.findAll();
    }

    public Entrada getEntradaById(Long id) {
        return entradaRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Não achou"));
    }

    public List<Entrada> findAllByStatusEntrada(StatusEntrada statusEntrada) {
        Entrada entrada = new Entrada();
        var status = entrada.getStatusEntrada();
        return entradaRepository.findAllByStatusEntrada(status);
    }

    public List<Entrada> findAllByDataRegistroEntrada(LocalDate data) {
        return entradaRepository.findAllByDataRegistroEntrada(data);
    }

    public List<Entrada> findAllByDataRegistroEntrada() {
        return entradaRepository.findAllByDataRegistroEntrada(LocalDate.now());
    }

    public Entrada createEntrada(Entrada entrada, Long idQuarto){
        var quarto = quartosRepository.findById(idQuarto).orElseThrow(()-> new EntityNotFoundException("Não há quarto com essa numeração"));
        entrada.setQuartos(quarto);
        entrada.getQuartos().setStatusDoQuarto(StatusDoQuarto.OCUPADO);

        LocalTime horaAtual = LocalTime.now();
        DateTimeFormatter formatterTimeString = DateTimeFormatter.ofPattern("HH:mm:ss");
        String horaString = horaAtual.format(formatterTimeString);
        LocalTime horaOficial = LocalTime.parse(horaString, formatterTimeString);
        entrada.setHoraEntrada(horaOficial);

        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatterDataString = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataString = dataAtual.format(formatterDataString);
        LocalDate dataOficial = LocalDate.parse(dataString, formatterDataString);
        entrada.setDataRegistroEntrada(dataOficial);

        return entradaRepository.save(entrada);
    }

    public Entrada updtateEntrada( Long idEntrada){
        Entrada entrada = entradaRepository.findById(idEntrada).
            orElseThrow(()-> new EntityNotFoundException("Não existe entrada com esse id"));

            entrada.setDataRegistroEntrada(entrada.getDataRegistroEntrada());
            entrada.setHoraEntrada(entrada.getHoraEntrada());
            entrada.setStatusEntrada(entrada.getStatusEntrada());
            entrada.setTipoPagamento(entrada.getTipoPagamento());
            entrada.setPlaca(entrada.getPlaca());
            entrada.setHoraSaida(entrada.getHoraSaida());

            /*
            var quarto = quartosRepository.findById(entrada.getQuartos().getId()).orElseThrow(() -> new EntityNotFoundException("Não há quarto com essa numeração"));
            entrada.setQuartos(quarto);
            */

            entrada.setStatusPagamento(entrada.getStatusPagamento());
            entrada.setTotalEntrada(entrada.getTotalEntrada());
            return entradaRepository.save(entrada);

    }
}
