package org.gabrielbarrilli.motelteste.repository;

import org.gabrielbarrilli.motelteste.enums.StatusEntrada;
import org.gabrielbarrilli.motelteste.model.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {

    List<Entrada> findAllByStatusEntrada(StatusEntrada statusEntrada);

    List<Entrada> findAllByDataRegistroEntrada(LocalDate dataRegistroEntrada);

}
