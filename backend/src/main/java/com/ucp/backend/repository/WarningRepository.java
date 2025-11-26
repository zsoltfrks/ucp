package com.ucp.backend.repository;

import com.ucp.backend.model.Warning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarningRepository extends JpaRepository<Warning, Long> {
    List<Warning> findByUserId(Long userId);
    
    @Query("SELECT w FROM Warning w " +
           "LEFT JOIN FETCH w.user")
    List<Warning> findAllWithUser();
}
