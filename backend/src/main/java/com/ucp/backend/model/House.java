package com.ucp.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "houses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private PlayerProfile owner;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private Long price;
    
    @Column(nullable = false)
    private Integer interiorId;
    
    @Column(nullable = false)
    private Boolean locked = false;
    
    @Column(name = "purchased_at", nullable = false)
    private LocalDateTime purchasedAt = LocalDateTime.now();
}
