package com.ucp.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminNoteDTO {
    private Long id;
    private Long userId;
    private String username;
    
    @NotBlank(message = "Note is required")
    private String note;
    
    private String createdBy;
    private LocalDateTime createdAt;
}
