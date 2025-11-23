package com.ucp.backend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private Boolean active;
    private LocalDateTime createdAt;
}
