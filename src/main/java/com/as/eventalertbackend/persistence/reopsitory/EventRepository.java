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
            SELECT id, ST_Distance_Sphere(point(latitude, longitude),point(?1, ?2)) / 1000 AS distance
            FROM event
            WHERE created_at BETWEEN ?4 AND ?5 
            AND type_id IN ?6 
            AND severity_id IN ?7
            HAVING distance <= ?3
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
            Set<String> severityIds);

    @Query(value = "SELECT * FROM event WHERE id in ?1",
            countQuery = "SELECT COUNT(*) FROM event WHERE id in ?1",
            nativeQuery = true)
    Page<Event> findByIds(long[] eventIds, Pageable pageable);

    List<Event> findByUserIdOrderByCreatedAtDesc(Long userId);

}
