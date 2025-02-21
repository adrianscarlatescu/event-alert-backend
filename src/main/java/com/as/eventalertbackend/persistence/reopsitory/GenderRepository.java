package com.as.eventalertbackend.persistence.reopsitory;

import com.as.eventalertbackend.enums.id.GenderId;
import com.as.eventalertbackend.persistence.entity.lookup.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenderRepository extends JpaRepository<Gender, GenderId> {

    List<Gender> findAllByOrderByPositionAsc();

}
