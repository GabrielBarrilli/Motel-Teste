package org.gabrielbarrilli.motelteste.service;

import jakarta.persistence.EntityNotFoundException;
import org.gabrielbarrilli.motelteste.Enum.StatusDoQuarto;
import org.gabrielbarrilli.motelteste.Enum.StatusEntrada;
import org.gabrielbarrilli.motelteste.Enum.StatusPagamento;
import org.gabrielbarrilli.motelteste.Enum.TipoPagamento;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.Quartos;
import org.gabrielbarrilli.motelteste.repository.EntradaConsumoRepository;
import org.gabrielbarrilli.motelteste.repository.EntradaRepository;
import org.gabrielbarrilli.motelteste.repository.QuartosRepository;
import org.gabrielbarrilli.motelteste.response.EntradaResponse;
import org.gabrielbarrilli.motelteste.response.HorarioResponse;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    public List<EntradaResponse> getAllEntrada() {
        List<Entrada> entradas = entradaRepository.findAll();
        List<EntradaResponse> entradaResponses = new ArrayList<>();

        for (Entrada entrada : entradas) {
            EntradaResponse response = new EntradaResponse(
                    entrada.getId(),
                    entrada.getNomeLocador(),
                    entrada.getDataRegistroEntrada(),
                    entrada.getHoraEntrada(),
                    entrada.getStatusEntrada(),
                    entrada.getTipoPagamento(),
                    entrada.getPlaca(),
                    entrada.getDataSaida(),
                    entrada.getHoraSaida(),
                    entrada.getQuartos().getNumero(),
                    entrada.getStatusPagamento(),
                    entrada.getTotalEntrada(),
                    calculoPermanencia(entrada.getId())
            );

            entradaResponses.add(response);
        }

        return entradaResponses;
    }

    public EntradaResponse getEntradaById(Long id) {
        var entrada = entradaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Não achou"));
        return new EntradaResponse(
                entrada.getId(),
                entrada.getNomeLocador(),
                entrada.getDataRegistroEntrada(),
                entrada.getHoraEntrada(),
                entrada.getStatusEntrada(),
                entrada.getTipoPagamento(),
                entrada.getPlaca(),
                entrada.getDataSaida(),
                entrada.getHoraSaida(),
                entrada.getQuartos().getNumero(),
                entrada.getStatusPagamento(),
                entrada.getTotalEntrada(),
                calculoPermanencia(id)
        );
    }

    public List<Entrada> findAllByStatusEntrada(StatusEntrada statusEntrada) {
        return entradaRepository.findAllByStatusEntrada(statusEntrada);
    }

    public List<Entrada> findAllByDataRegistroEntrada(LocalDate data) {
        return entradaRepository.findAllByDataRegistroEntrada(data);
    }

    public List<Entrada> findAllByDataRegistroEntrada() {
        return entradaRepository.findAllByDataRegistroEntrada(LocalDate.now());
    }

    public Entrada createEntrada(Entrada entrada, Long idQuarto) {
        var quarto = quartosRepository.findById(idQuarto).orElseThrow(() -> new EntityNotFoundException("Não há quarto com essa numeração"));
        entrada.setQuartos(quarto);
        entrada.getQuartos().setStatusDoQuarto(StatusDoQuarto.OCUPADO);
        entrada.setStatusEntrada(StatusEntrada.ATIVA);
        entrada.setTipoPagamento(TipoPagamento.PENDENTE);
        entrada.setHoraSaida(null);

        entrada.setStatusPagamento(StatusPagamento.PENDENTE);
        entrada.setTotalEntrada(null);

        entrada.setHoraEntrada(LocalTime.now());

        entrada.setDataRegistroEntrada(LocalDate.now());

        return entradaRepository.save(entrada);
    }

    public Entrada updtateEntrada(Long idEntrada, Entrada entradaExistente, Long idNovoQuarto) {

        entradaExistente = entradaRepository.findById(idEntrada).
                orElseThrow(() -> new EntityNotFoundException("Entrada não encontrada!"));

        Quartos novoQuarto = quartosRepository.findById(idNovoQuarto).
                orElseThrow(() -> new EntityNotFoundException("Quarto não encontrado"));

        if (entradaExistente.getQuartos().getId().equals(novoQuarto.getId())) {
            entradaExistente.getQuartos().setStatusDoQuarto(StatusDoQuarto.OCUPADO);

            entradaExistente.setQuartos(novoQuarto);

        } else {
            if (novoQuarto.getStatusDoQuarto() != StatusDoQuarto.OCUPADO ||
                    novoQuarto.getStatusDoQuarto() != StatusDoQuarto.RESERVADO) {
                novoQuarto.setStatusDoQuarto(StatusDoQuarto.OCUPADO);
                entradaExistente.getQuartos().setStatusDoQuarto(StatusDoQuarto.DISPONIVEL);
                entradaExistente.setQuartos(novoQuarto);

            } else if (novoQuarto.getStatusDoQuarto() == StatusDoQuarto.OCUPADO) {
                throw new EntityNotFoundException("Quarto está ocupado!");
            } else if (novoQuarto.getStatusDoQuarto() == StatusDoQuarto.NECESSITA_LIMPEZA) {
                throw new EntityNotFoundException("Quarto está para limpeza!");
            }
        }

        entradaExistente.setDataRegistroEntrada(entradaExistente.getDataRegistroEntrada());
        entradaExistente.setHoraEntrada(entradaExistente.getHoraEntrada());
        entradaExistente.setStatusEntrada(entradaExistente.getStatusEntrada());
        entradaExistente.setTipoPagamento(entradaExistente.getTipoPagamento());
        entradaExistente.setDataSaida(entradaExistente.getDataSaida());
        entradaExistente.setHoraSaida(entradaExistente.getHoraSaida());
        entradaExistente.setStatusPagamento(entradaExistente.getStatusPagamento());
        entradaExistente.setTotalEntrada(entradaExistente.getTotalEntrada());

        return entradaRepository.save(entradaExistente);
    }

    public EntradaResponse finalizarEntrada(Long idEntrada, TipoPagamento tipoPagamento) {
        Entrada entrada = entradaRepository.findById(idEntrada).
                orElseThrow(() -> new EntityNotFoundException("Entrada não encontrada!"));
        if(entrada.getStatusEntrada() != StatusEntrada.FINALIZADA) {
            if (tipoPagamento != TipoPagamento.PENDENTE) {
                entrada.setStatusEntrada(StatusEntrada.FINALIZADA);
                entrada.setTipoPagamento(tipoPagamento);
                entrada.setDataSaida(LocalDate.now());
                entrada.setHoraSaida(LocalTime.now());
                entrada.setStatusPagamento(StatusPagamento.PAGO);
                calculoPermanencia(idEntrada);
            } else {
                throw new IllegalArgumentException("O pagamento não pode estar pendente, selecione uma opção de pagamento!");
            }
        } else {
            throw new IllegalArgumentException("A entrada já foi finalizada!");
        }
        return new EntradaResponse(entrada.getId(),
                entrada.getNomeLocador(),
                entrada.getDataRegistroEntrada(),
                entrada.getHoraEntrada(),
                entrada.getStatusEntrada(),
                entrada.getTipoPagamento(),
                entrada.getPlaca(),
                entrada.getDataSaida(),
                entrada.getHoraSaida(),
                entrada.getQuartos().getNumero(),
                entrada.getStatusPagamento(),
                entrada.getTotalEntrada(),
                calculoPermanencia(idEntrada)
        );
    }

    public HorarioResponse calculoPermanencia(Long idEntrada) {

        var entrada = entradaRepository.findById(idEntrada).orElseThrow(() -> new EntityNotFoundException("Não achou id para calcular"));

        if (entrada.getHoraEntrada() != null && entrada.getHoraSaida() != null) {
            LocalDate dataEntrada = entrada.getDataRegistroEntrada();
            LocalDate dataSaida = entrada.getDataSaida();
            LocalTime horaEntrada = entrada.getHoraEntrada();
            LocalTime horaSaida = entrada.getHoraSaida();

            var duration = Duration.between(dataEntrada.atTime(horaEntrada), dataSaida.atTime(horaSaida));

            long horas = duration.toHours();
            long minutos = duration.toMinutes() % 60;
            long segundos = duration.toSeconds() % 60;

            return new HorarioResponse(horas, minutos, segundos);
        } else {
            return new HorarioResponse(Duration.ZERO.toHours(), Duration.ZERO.toMinutes(), Duration.ZERO.toSeconds());
        }
    }
}
