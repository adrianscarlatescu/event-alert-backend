package com.as.eventalertbackend.data.reopsitory;

import com.as.eventalertbackend.data.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query(value = "SELECT user_id " +
            "FROM subscription " +
            "WHERE ST_Distance_Sphere(point(latitude, longitude),point(?1, ?2)) / 1000 <= radius " +
            "AND user_id != ?3",
            nativeQuery = true)
    List<Long> findUsersIdsByFilter(double eventLatitude, double eventLongitude, long excludedUserId);

    boolean existsByUserId(Long id);

    Optional<Subscription> findByUserId(Long id);

    void deleteByUserId(Long id);

}
