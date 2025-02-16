package com.as.eventalertbackend.persistence.reopsitory;

import com.as.eventalertbackend.persistence.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {

    boolean existsByCode(String code);

    @Query("SELECT COUNT(e) > 0 FROM Event e WHERE e.type.id = :typeId")
    boolean existsEventByTypeId(Long typeId);

}
