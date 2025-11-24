package com.ucp.backend.repository;

import com.ucp.backend.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    List<House> findByOwnerId(Long ownerId);
    
    @Query("SELECT h FROM House h " +
           "LEFT JOIN FETCH h.owner o " +
           "LEFT JOIN FETCH o.user")
    List<House> findAllWithOwnerAndUser();
    
    @Query("SELECT h FROM House h " +
           "LEFT JOIN FETCH h.owner o " +
           "LEFT JOIN FETCH o.user " +
           "WHERE o.id = :ownerId")
    List<House> findByOwnerIdWithUser(Long ownerId);
}
