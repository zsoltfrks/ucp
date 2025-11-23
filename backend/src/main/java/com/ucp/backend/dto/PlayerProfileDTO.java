package com.ucp.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PlayerProfileDTO {
    private Long id;
    private Long userId;
    private String username;
    private String characterName;
    private Integer level;
    private Long money;
    private Integer playedHours;
    private LocalDateTime lastLogin;
    private Integer housesCount;
    private Integer vehiclesCount;
}
