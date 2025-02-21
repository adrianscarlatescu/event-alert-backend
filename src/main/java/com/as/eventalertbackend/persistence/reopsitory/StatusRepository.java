package com.as.eventalertbackend.persistence.reopsitory;

import com.as.eventalertbackend.persistence.entity.lookup.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status, String> {

    List<Status> findAllByOrderByPositionAsc();

    boolean existsByPositionAndIdNot(Integer position, String id);

    @Query("SELECT COUNT(e) > 0 FROM Event e WHERE e.status.id = :statusId")
    boolean existsEventByStatusId(String statusId);

}
