package org.gabrielbarrilli.motelteste.service;

import jakarta.persistence.EntityNotFoundException;
import org.gabrielbarrilli.motelteste.Enum.StatusDoQuarto;
import org.gabrielbarrilli.motelteste.Enum.StatusEntrada;
import org.gabrielbarrilli.motelteste.Enum.StatusPagamento;
import org.gabrielbarrilli.motelteste.Enum.TipoPagamento;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.EntradaConsumo;
import org.gabrielbarrilli.motelteste.model.Quartos;
import org.gabrielbarrilli.motelteste.repository.EntradaConsumoRepository;
import org.gabrielbarrilli.motelteste.repository.EntradaRepository;
import org.gabrielbarrilli.motelteste.repository.QuartosRepository;
import org.gabrielbarrilli.motelteste.request.CriarEntradaRequest;
import org.gabrielbarrilli.motelteste.request.EntradaRequest;
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

    private static final Float VALOR_ENTRADA = 30F;
    private final EntradaRepository entradaRepository;
    private final QuartosRepository quartosRepository;
    private final EntradaConsumoRepository entradaConsumoRepository;

    public EntradaService(EntradaRepository entradaRepository, QuartosRepository quartoRepository,
                          EntradaConsumoRepository entradaConsumoRepository) {
        this.entradaRepository = entradaRepository;
        this.quartosRepository = quartoRepository;
        this.entradaConsumoRepository = entradaConsumoRepository;
    }

    public List<EntradaResponse> getAllEntrada() {
        List<Entrada> entradas = entradaRepository.findAll();
        List<EntradaResponse> entradaResponses = new ArrayList<>();

        entradas.forEach(entrada1 -> {
            EntradaResponse response = new EntradaResponse(
                    entrada1.getId(),
                    entrada1.getNomeLocador(),
                    entrada1.getDataRegistroEntrada(),
                    entrada1.getHoraEntrada(),
                    entrada1.getStatusEntrada(),
                    entrada1.getTipoPagamento(),
                    entrada1.getPlaca(),
                    entrada1.getDataSaida(),
                    entrada1.getHoraSaida(),
                    entrada1.getQuartos().getNumero(),
                    entrada1.getStatusPagamento(),
                    entrada1.getTotalEntrada()
            );
            entradaResponses.add(response);
        });

        return entradaResponses;
    }

    public EntradaResponse getEntradaById(Long id) {
        var entrada = entradaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Não achou"));
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
        Entrada entrada = new Entrada();
        var quarto = quartosRepository.findById(idQuarto).orElseThrow(() -> new EntityNotFoundException("Não há quarto com essa numeração"));

        entrada.setNomeLocador(criarEntradaRequest.nomeLocador());
        entrada.setPlaca(criarEntradaRequest.placa());
        entrada.setQuartos(quarto);
        entrada.getQuartos().setStatusDoQuarto(StatusDoQuarto.OCUPADO);
        entrada.setStatusEntrada(StatusEntrada.ATIVA);
        entrada.setTipoPagamento(TipoPagamento.PENDENTE);
        entrada.setHoraSaida(null);

        entrada.setStatusPagamento(StatusPagamento.PENDENTE);
        entrada.setTotalEntrada(VALOR_ENTRADA);

        entrada.setHoraEntrada(LocalTime.now());

        entrada.setDataRegistroEntrada(LocalDate.now());

        return entradaRepository.save(entrada);
    }

    public Entrada updtateEntrada(Long idEntrada, EntradaRequest entradaRequest, Long idNovoQuarto) {

        var entradaExistente = entradaRepository.findById(idEntrada).
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

        entradaExistente.setNomeLocador(entradaRequest.nomeLocador());
        entradaExistente.setPlaca(entradaRequest.placa());

        return entradaRepository.save(entradaExistente);
    }

    public EntradaResponse finalizarEntrada(Long idEntrada, TipoPagamento tipoPagamento) {
        Entrada entrada = entradaRepository.findById(idEntrada).
                orElseThrow(() -> new EntityNotFoundException("Entrada não encontrada!"));

        if (entrada.getStatusEntrada() != StatusEntrada.FINALIZADA) {
            if (tipoPagamento != TipoPagamento.PENDENTE) {
                entrada.setStatusEntrada(StatusEntrada.FINALIZADA);
                entrada.setTipoPagamento(tipoPagamento);
                entrada.setDataSaida(LocalDate.now());
                entrada.setHoraSaida(LocalTime.now());
                entrada.setStatusPagamento(StatusPagamento.PAGO);
                entrada.setTotalEntrada(calculoTotalEntrada(idEntrada));
                // calculoPermanencia(idEntrada);
            } else {
                throw new IllegalArgumentException("O pagamento não pode estar pendente, selecione uma opção de pagamento!");
            }
        } else {
            throw new IllegalArgumentException("A entrada já foi finalizada!");
        }

        return entradaResponse(entrada);
    }


    public Float calculoTotalEntrada(Long idEntrada) {

        var entrada = entradaRepository.findById(idEntrada).orElseThrow(() -> new EntityNotFoundException("Não achou id para calcular"));

        float valorEstadia = 0;

        if(entrada.getStatusEntrada() != StatusEntrada.FINALIZADA) {
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
                    valorEstadia = entrada.getTotalEntrada() + (mins * 5);
                }

                entrada.setTotalEntrada(calculoTotalEntrada(idEntrada));
                return valorEstadia + entrada.getTotalEntrada();

            } else {
                LocalDate dataEntrada = entrada.getDataRegistroEntrada();
                LocalTime horaEntrada = entrada.getHoraEntrada();

                var duration = Duration.between(dataEntrada.atTime(horaEntrada), LocalDate.now().atTime(LocalTime.now()));

                var horas = duration.toHours();

                if (horas > 2) {
                    long totalMinutos = duration.toMinutes();
                    int mins = (int) (totalMinutos / 30);
                    valorEstadia = 30 + (mins * 5);

                }

                return valorEstadia + entrada.getTotalEntrada();
            }
        } else {
            throw new IllegalArgumentException("A entrada já foi finalizada!");
        }
    }



   /* public Float calculoTotalEntrada(Long idEntrada) {

    }
*/
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

}



