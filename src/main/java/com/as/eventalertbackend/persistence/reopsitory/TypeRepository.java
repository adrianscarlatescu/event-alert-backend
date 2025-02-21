package com.as.eventalertbackend.persistence.reopsitory;

import com.as.eventalertbackend.persistence.entity.lookup.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<Type, String> {

    List<Type> findAllByOrderByPositionAsc();

    boolean existsByPosition(Integer position);

    @Query("SELECT COUNT(e) > 0 FROM Event e WHERE e.type.id = :typeId")
    boolean existsEventByTypeId(String typeId);

}
