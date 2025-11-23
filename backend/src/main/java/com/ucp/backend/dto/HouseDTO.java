package com.ucp.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HouseDTO {
    private Long id;
    private Long ownerId;
    private String ownerName;
    private String address;
    private Long price;
    private Integer interiorId;
    private Boolean locked;
    private LocalDateTime purchasedAt;
}
