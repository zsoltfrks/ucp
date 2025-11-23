package com.ucp.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WarningDTO {
    private Long id;
    private Long userId;
    private String username;
    
    @NotBlank(message = "Reason is required")
    private String reason;
    
    private String warnedBy;
    private LocalDateTime warnedAt;
    private Boolean acknowledged;
}
