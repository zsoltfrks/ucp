package com.ucp.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "warnings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Warning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private String reason;
    
    @Column(name = "warned_by", nullable = false)
    private String warnedBy;
    
    @Column(name = "warned_at", nullable = false)
    private LocalDateTime warnedAt = LocalDateTime.now();
    
    @Column(nullable = false)
    private Boolean acknowledged = false;
}
