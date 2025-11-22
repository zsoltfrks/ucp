package com.ucp.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VehicleDTO {
    private Long id;
    private Long ownerId;
    private String ownerName;
    private String model;
    private String color;
    private String plateNumber;
    private Long price;
    private Integer health;
    private Boolean impounded;
    private LocalDateTime purchasedAt;
}
