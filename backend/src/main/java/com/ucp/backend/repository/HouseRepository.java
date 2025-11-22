package com.ucp.backend.repository;

import com.ucp.backend.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    List<House> findByOwnerId(Long ownerId);
}
