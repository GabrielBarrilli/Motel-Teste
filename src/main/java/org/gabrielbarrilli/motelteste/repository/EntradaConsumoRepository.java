package org.gabrielbarrilli.motelteste.repository;

import org.gabrielbarrilli.motelteste.model.Entrada;
import org.gabrielbarrilli.motelteste.model.EntradaConsumo;
import org.gabrielbarrilli.motelteste.model.Itens;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EntradaConsumoRepository extends JpaRepository<EntradaConsumo, Long> {

    List<EntradaConsumo> findAllByEntradaId(Long idEntrada);
}
