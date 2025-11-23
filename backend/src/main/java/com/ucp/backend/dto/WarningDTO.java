package com.ucp.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
