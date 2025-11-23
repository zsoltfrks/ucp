package com.ucp.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminNoteDTO {
    private Long id;
    private Long userId;
    private String username;
    
    @NotBlank(message = "Note is required")
    private String note;
    
    private String createdBy;
    private LocalDateTime createdAt;
}
