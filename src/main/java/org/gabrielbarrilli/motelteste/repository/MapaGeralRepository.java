package org.gabrielbarrilli.motelteste.repository;

import org.gabrielbarrilli.motelteste.model.MapaGeral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MapaGeralRepository extends JpaRepository<MapaGeral, Long> {
    @Query("SELECT SUM(m.total) FROM MapaGeral m WHERE m.total IS NOT NULL")
    Float calculaTotal();
}
