package com.as.eventalertbackend.persistence.reopsitory;

import com.as.eventalertbackend.persistence.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query(value = "SELECT * " +
            "FROM subscription " +
            "WHERE ST_Distance_Sphere(point(latitude, longitude),point(?1, ?2)) / 1000 <= radius " +
            "AND user_id != ?3",
            nativeQuery = true)
    List<Subscription> findByLocation(Double eventLatitude, Double eventLongitude, Long userIdToExclude);

    boolean existsByUserIdAndDeviceToken(Long userId, String deviceToken);

    Optional<Subscription> findByUserIdAndDeviceToken(Long userId, String deviceToken);

    void deleteByUserIdAndDeviceToken(Long userId, String deviceToken);

}
