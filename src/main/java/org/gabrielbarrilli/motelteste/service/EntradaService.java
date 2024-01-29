package org.gabrielbarrilli.motelteste.service;

import jakarta.persistence.EntityNotFoundException;
import org.gabrielbarrilli.motelteste.Enum.StatusDoQuarto;
import org.gabrielbarrilli.motelteste.Enum.StatusEntrada;
import org.gabrielbarrilli.motelteste.Enum.StatusPagamento;
import org.gabrielbarrilli.motelteste.Enum.TipoPagamento;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.Quartos;
import org.gabrielbarrilli.motelteste.model.builders.EntradaBuilder;
import org.gabrielbarrilli.motelteste.repository.EntradaRepository;
import org.gabrielbarrilli.motelteste.repository.MapaGeralRepository;
import org.gabrielbarrilli.motelteste.repository.QuartosRepository;
import org.gabrielbarrilli.motelteste.request.CriarEntradaRequest;
import org.gabrielbarrilli.motelteste.request.EntradaRequest;
import org.gabrielbarrilli.motelteste.response.EntradaResponse;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EntradaService {

    private static final Float VALOR_ENTRADA = 30F;
    private final EntradaRepository entradaRepository;
    private final QuartosRepository quartosRepository;
    private final MapaGeralRepository mapaGeralRepository;

    public EntradaService(EntradaRepository entradaRepository, QuartosRepository quartoRepository, MapaGeralRepository mapaGeralRepository) {
        this.entradaRepository = entradaRepository;
        this.quartosRepository = quartoRepository;
        this.mapaGeralRepository = mapaGeralRepository;
    }

    private EntradaResponse entradaResponse(Entrada entrada) {
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
                entrada.getTotalEntrada());
    }

    public List<EntradaResponse> getAllEntrada() {
        List<Entrada> entradas = entradaRepository.findAll();
        List<EntradaResponse> entradaResponses = new ArrayList<>();

        entradas.forEach(entrada1 -> {
            var response = entradaResponse(entrada1);
            entradaResponses.add(response);
        });

        return entradaResponses;
    }

    public EntradaResponse getEntradaById(Long id) {
        var entrada = entradaRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Não achou"));
        return entradaResponse(entrada);
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

    public Entrada createEntrada(CriarEntradaRequest criarEntradaRequest, Long idQuarto) {
        var quarto = quartosRepository.findById(idQuarto).
                orElseThrow(() -> new EntityNotFoundException("Não há quarto com essa numeração"));

        if (quarto.getStatusDoQuarto() == StatusDoQuarto.DISPONIVEL) {
            quarto.setStatusDoQuarto(StatusDoQuarto.OCUPADO);
            Entrada entrada = new EntradaBuilder().
                    nomeLocador(criarEntradaRequest.nomeLocador()).
                    placa(criarEntradaRequest.placa()).
                    quartos(quarto).
                    statusEntrada(StatusEntrada.ATIVA).
                    tipoPagamento(TipoPagamento.PENDENTE).
                    horaSaida(null).
                    statusPagamento(StatusPagamento.PENDENTE).
                    totalEntrada(VALOR_ENTRADA).
                    horaEntrada(LocalTime.now()).
                    dataRegistroEntrada(LocalDate.now()).
                    build();
            return entradaRepository.save(entrada);
        } else {
            throw new EntityNotFoundException("O quarto está indisponível! ");
        }
    }

    public Entrada updateEntrada(Long idEntrada, EntradaRequest entradaRequest, StatusEntrada statusEntrada, TipoPagamento tipoPagamento) {
        MapaGeralService mapaGeralService = new MapaGeralService(mapaGeralRepository, entradaRepository);
        Entrada entrada = entradaRepository.findById(idEntrada).
                orElseThrow(() -> new EntityNotFoundException("Entrada não encontrada!"));

        if (entrada.getStatusEntrada().equals(StatusEntrada.FINALIZADA)) {
            throw new IllegalArgumentException("A entrada já está finalizada");
        }

        entrada.setNomeLocador(entradaRequest.nomeLocador());
        entrada.setPlaca(entradaRequest.placa());
        entrada.setStatusEntrada(statusEntrada);
        entrada.setTipoPagamento(tipoPagamento);
        updateQuarto(idEntrada, entradaRequest);

        if (entrada.getStatusEntrada().equals(StatusEntrada.FINALIZADA)) {
            finalizarEntrada(idEntrada, entrada.getTipoPagamento());
            mapaGeralService.criarMapa(idEntrada);
        }

        return entradaRepository.save(entrada);
    }

    private void finalizarEntrada(Long idEntrada, TipoPagamento tipoPagamento) {
        Entrada entrada = entradaRepository.findById(idEntrada).
                orElseThrow(() -> new EntityNotFoundException("Entrada não encontrada!"));

        if (tipoPagamento == TipoPagamento.PENDENTE) {
            throw new IllegalArgumentException("O pagamento não pode estar pendente, selecione uma opção de pagamento!");
        }

        entrada.setDataSaida(LocalDate.now());
        entrada.setHoraSaida(LocalTime.now());
        entrada.setStatusPagamento(StatusPagamento.PAGO);
        entrada.getQuartos().setStatusDoQuarto(StatusDoQuarto.DISPONIVEL);
        calculoTotalEntradaTempo(entrada);
        entradaRepository.save(entrada);

    }

    public void calculoTotalEntradaTempo(Entrada entrada) {

        if (entrada.getDataSaida() != null && entrada.getHoraSaida() != null) {
            LocalDate dataEntrada = entrada.getDataRegistroEntrada();
            LocalDate dataSaida = entrada.getDataSaida();
            LocalTime horaEntrada = entrada.getHoraEntrada();
            LocalTime horaSaida = entrada.getHoraSaida();

            var duration = Duration.between(dataEntrada.atTime(horaEntrada), dataSaida.atTime(horaSaida));

            long horas = duration.toHours();

            if (horas > 2) {
                long totalMinutos = duration.toMinutes();
                int mins = (int) (totalMinutos / 30);
                float valorEstadia = (mins * 5);
                entrada.setTotalEntrada(entrada.getTotalEntrada() + valorEstadia);
            }
        }
    }

    private void updateQuarto(Long idEntrada, EntradaRequest entradaRequest) {
        Entrada entrada = entradaRepository.findById(idEntrada).
                orElseThrow(() -> new EntityNotFoundException("Não há entrada com esse id"));
        Quartos quarto = quartosRepository.findById(entradaRequest.idQuarto()).
                orElseThrow(() -> new EntityNotFoundException("Não há quarto com esse id"));

        if (!entradaRequest.idQuarto().equals(entrada.getQuartos().getId())) {
            switch (quarto.getStatusDoQuarto()) {
                case OCUPADO -> throw new IllegalArgumentException("O quarto está ocupado!");

                case NECESSITA_LIMPEZA -> throw new IllegalArgumentException("O quarto necessita limpeza!");

                case RESERVADO -> throw new IllegalArgumentException("O quarto está reservado!");
            }
            entrada.getQuartos().setStatusDoQuarto(StatusDoQuarto.DISPONIVEL);
            quarto.setStatusDoQuarto(StatusDoQuarto.OCUPADO);
            entrada.setQuartos(quarto);
            quartosRepository.save(quarto);
        }

    }

}



