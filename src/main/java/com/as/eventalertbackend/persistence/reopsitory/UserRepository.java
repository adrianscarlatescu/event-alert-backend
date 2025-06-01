package com.as.eventalertbackend.persistence.reopsitory;

import com.as.eventalertbackend.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT COUNT(e) > 0 FROM Event e WHERE e.user.id = :userId")
    boolean existsEventByUserId(Long userId);

    @Query("SELECT COUNT(c) > 0 FROM Comment c WHERE c.user.id = :userId")
    boolean existsCommentByUserId(Long userId);

}
