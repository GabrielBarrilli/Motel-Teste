package org.gabrielbarrilli.motelteste.repository;

import org.gabrielbarrilli.motelteste.model.Quartos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuartosRepository extends JpaRepository<Quartos, Long> {
}
