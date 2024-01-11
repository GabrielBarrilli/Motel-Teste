package org.gabrielbarrilli.motelteste.repository;

import org.gabrielbarrilli.motelteste.model.Troco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrocoRepository extends JpaRepository<Troco, Long>{
}
