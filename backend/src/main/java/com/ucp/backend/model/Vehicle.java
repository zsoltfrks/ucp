package com.ucp.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private PlayerProfile owner;
    
    @Column(nullable = false)
    private String model;
    
    @Column(nullable = false)
    private String color;
    
    @Column(nullable = false)
    private String plateNumber;
    
    @Column(nullable = false)
    private Long price;
    
    @Column(nullable = false)
    private Integer health = 1000;
    
    @Column(nullable = false)
    private Boolean impounded = false;
    
    @Column(name = "purchased_at", nullable = false)
    private LocalDateTime purchasedAt = LocalDateTime.now();
}
