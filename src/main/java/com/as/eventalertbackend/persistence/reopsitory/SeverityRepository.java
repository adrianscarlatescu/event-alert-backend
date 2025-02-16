package com.as.eventalertbackend.persistence.reopsitory;

import com.as.eventalertbackend.persistence.entity.Severity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SeverityRepository extends JpaRepository<Severity, Long> {

    boolean existsByCode(String code);

    @Query("SELECT COUNT(e) > 0 FROM Event e WHERE e.severity.id = :severityId")
    boolean existsEventBySeverityId(Long severityId);

}
