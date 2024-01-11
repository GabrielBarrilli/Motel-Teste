package org.gabrielbarrilli.motelteste.repository;

import org.gabrielbarrilli.motelteste.Enum.StatusPagamento;
import org.gabrielbarrilli.motelteste.Enum.TipoPagamento;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    List<Entrada> findByDataRegistroEntrada(LocalDate dataRegistroEntrada);
    List<Entrada> findByStatusEntrada(LocalDate statusEntrada);
    List<Entrada> findByTipoPagamento(TipoPagamento tipoPagamento);
    List<Entrada> findByPlaca(String placa);
    List<Entrada> findByHoraSaida(LocalTime horaSaida);
    List<Entrada> findByStatusPagamento(StatusPagamento statusPagamento);
}
