package org.gabrielbarrilli.motelteste.repository;

import org.gabrielbarrilli.motelteste.model.Itens;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensRepository extends JpaRepository<Itens, Long> {
}
