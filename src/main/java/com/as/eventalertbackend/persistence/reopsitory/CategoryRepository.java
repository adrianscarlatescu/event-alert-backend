package com.as.eventalertbackend.persistence.reopsitory;

import com.as.eventalertbackend.persistence.entity.lookup.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    List<Category> findAllByOrderByLabelAsc();

    boolean existsByPosition(Integer position);

    @Query("SELECT COUNT(t) > 0 FROM Type t WHERE t.category.id = :categoryId")
    boolean existsTypeByCategoryCode(String categoryId);

}
