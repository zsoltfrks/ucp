package com.ucp.backend.repository;

import com.ucp.backend.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByOwnerId(Long ownerId);
    
    @Query("SELECT v FROM Vehicle v " +
           "LEFT JOIN FETCH v.owner o " +
           "LEFT JOIN FETCH o.user")
    List<Vehicle> findAllWithOwnerAndUser();
    
    @Query("SELECT v FROM Vehicle v " +
           "LEFT JOIN FETCH v.owner o " +
           "LEFT JOIN FETCH o.user " +
           "WHERE o.id = :ownerId")
    List<Vehicle> findByOwnerIdWithUser(Long ownerId);
}
