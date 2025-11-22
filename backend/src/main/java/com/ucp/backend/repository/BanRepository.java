package com.ucp.backend.repository;

import com.ucp.backend.model.Ban;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BanRepository extends JpaRepository<Ban, Long> {
    List<Ban> findByUserId(Long userId);
    List<Ban> findByUserIdAndActiveTrue(Long userId);
}
