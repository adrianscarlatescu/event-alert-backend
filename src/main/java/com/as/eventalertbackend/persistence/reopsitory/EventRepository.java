package com.as.eventalertbackend.persistence.reopsitory;

import com.as.eventalertbackend.persistence.entity.Event;
import com.as.eventalertbackend.persistence.projection.EventProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = """
            SELECT id, impact_radius, ST_Distance_Sphere(point(longitude, latitude), point(:longitude, :latitude)) / 1000 AS distance
            FROM event
            WHERE created_at BETWEEN :startDate AND :endDate 
            AND type_id IN :typeIds 
            AND severity_id IN :severityIds
            AND status_id IN :statusIds
            HAVING distance <= :radius
            OR (impact_radius + :radius) >= distance
            ORDER BY distance ASC
            """,
            nativeQuery = true)
    List<EventProjection> findByFilter(
            Double latitude,
            Double longitude,
            Integer radius,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Set<String> typeIds,
            Set<String> severityIds,
            Set<String> statusIds);

    @Query(value = "SELECT e.*, s.position AS severity_position FROM event e LEFT JOIN ref_severity s ON s.id = e.severity_id WHERE e.id IN :eventIds",
            countQuery = "SELECT COUNT(id) FROM event WHERE id IN :eventIds",
            nativeQuery = true)
    Page<Event> findByIds(long[] eventIds, Pageable pageable);

    List<Event> findByUserIdOrderByCreatedAtDesc(Long userId);

}
