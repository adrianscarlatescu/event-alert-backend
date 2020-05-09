package com.as.eventalertbackend.data.reopsitory;

import com.as.eventalertbackend.data.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "SELECT id, ST_Distance_Sphere(point(latitude, longitude),point(?1, ?2)) / 1000 AS distance " +
            "FROM event " +
            "WHERE date_time BETWEEN ?4 AND ?5 AND tag_id IN ?6 AND severity_id IN ?7 " +
            "HAVING distance <= ?3 " +
            "ORDER BY distance ASC",
            nativeQuery = true)
    List<EventProjection> findByFilter(
            double latitude, double longitude, int radius,
            LocalDateTime startDate, LocalDateTime endDate,
            long[] tagsIds, long[] severitiesIds);

    @Query(value = "SELECT * FROM event WHERE id in ?1 AND date_time BETWEEN ?2 AND ?3 AND tag_id IN ?4 AND severity_id IN ?5 ",
            countQuery = "SELECT COUNT(*) FROM event WHERE id in ?1 AND date_time BETWEEN ?2 AND ?3 AND tag_id IN ?4 AND severity_id IN ?5",
            nativeQuery = true)
    Page<Event> findByFilter(
            long[] eventsIds,
            LocalDateTime startDate, LocalDateTime endDate,
            long[] tagsIds, long[] severitiesIds,
            Pageable pageable);

    List<Event> findByUserIdOrderByDateTimeDesc(Long userId);

    interface EventProjection {
        Long getId();

        Double getDistance();
    }

}