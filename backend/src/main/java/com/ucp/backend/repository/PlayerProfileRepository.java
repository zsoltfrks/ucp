package com.ucp.backend.repository;

import com.ucp.backend.model.PlayerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerProfileRepository extends JpaRepository<PlayerProfile, Long> {
    Optional<PlayerProfile> findByUserId(Long userId);
    
    /**
     * Fetches all player profiles with their associated user, houses, and vehicles.
     * Uses DISTINCT to eliminate duplicates caused by JOIN FETCH.
     * 
     * Note: For very large datasets (>10k records), consider implementing pagination
     * or using @EntityGraph to avoid memory issues with DISTINCT + multiple JOIN FETCH.
     */
    @Query("SELECT DISTINCT p FROM PlayerProfile p " +
           "LEFT JOIN FETCH p.user " +
           "LEFT JOIN FETCH p.houses " +
           "LEFT JOIN FETCH p.vehicles")
    List<PlayerProfile> findAllWithUserAndAssets();
    
    @Query("SELECT p FROM PlayerProfile p " +
           "LEFT JOIN FETCH p.user " +
           "LEFT JOIN FETCH p.houses " +
           "LEFT JOIN FETCH p.vehicles " +
           "WHERE p.id = :id")
    Optional<PlayerProfile> findByIdWithUserAndAssets(Long id);
    
    @Query("SELECT p FROM PlayerProfile p " +
           "LEFT JOIN FETCH p.user " +
           "LEFT JOIN FETCH p.houses " +
           "LEFT JOIN FETCH p.vehicles " +
           "WHERE p.user.id = :userId")
    Optional<PlayerProfile> findByUserIdWithAssets(Long userId);
}
