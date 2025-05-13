package com.as.eventalertbackend.persistence.reopsitory;

import com.as.eventalertbackend.persistence.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query(value = """
            SELECT * 
            FROM subscription
            WHERE (
                ST_Distance_Sphere(point(longitude, latitude), point(:eventLongitude, :eventLatitude)) / 1000 <= radius
                OR
                (:eventImpactRadius + radius) >= ST_Distance_Sphere(point(longitude, latitude), point(:eventLongitude, :eventLatitude)) / 1000
            ) 
            AND user_id != :userIdToExclude
            AND is_active = true
            """,
            nativeQuery = true)
    List<Subscription> findByLocation(Double eventLatitude, Double eventLongitude, BigDecimal eventImpactRadius, Long userIdToExclude);

    boolean existsByUserIdAndDeviceId(Long userId, String deviceId);

    Optional<Subscription> findByUserIdAndDeviceId(Long userId, String deviceId);

    List<Subscription> findAllByDeviceId(String deviceId);

    void deleteByUserIdAndDeviceId(Long userId, String deviceId);

}
