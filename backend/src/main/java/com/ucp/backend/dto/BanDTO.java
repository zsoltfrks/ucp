package com.ucp.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BanDTO {
    private Long id;
    private Long userId;
    private String username;
    
    @NotBlank(message = "Reason is required")
    private String reason;
    
    private String bannedBy;
    private LocalDateTime bannedAt;
    private LocalDateTime expiresAt;
    private Boolean active;
}
