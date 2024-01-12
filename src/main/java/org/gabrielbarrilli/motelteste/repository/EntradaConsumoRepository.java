package org.gabrielbarrilli.motelteste.repository;

import org.gabrielbarrilli.motelteste.model.EntradaConsumo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntradaConsumoRepository extends JpaRepository<EntradaConsumo, Long> {

}
