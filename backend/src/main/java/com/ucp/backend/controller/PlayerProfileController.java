package com.ucp.backend.controller;

import com.ucp.backend.dto.PlayerProfileDTO;
import com.ucp.backend.service.PlayerProfileService;
import com.ucp.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class PlayerProfileController {
    
    private final PlayerProfileService playerProfileService;
    private final UserService userService;
    
    @GetMapping("/me")
    public ResponseEntity<PlayerProfileDTO> getMyProfile(Authentication authentication) {
        Long userId = userService.getUserByUsername(authentication.getName()).getId();
        return ResponseEntity.ok(playerProfileService.getProfileByUserId(userId));
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LEAD_ADMIN')")
    public ResponseEntity<List<PlayerProfileDTO>> getAllProfiles() {
        return ResponseEntity.ok(playerProfileService.getAllProfiles());
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LEAD_ADMIN')")
    public ResponseEntity<PlayerProfileDTO> getProfileById(@PathVariable Long id) {
        return ResponseEntity.ok(playerProfileService.getProfileById(id));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LEAD_ADMIN')")
    public ResponseEntity<PlayerProfileDTO> updateProfile(@PathVariable Long id, @RequestBody PlayerProfileDTO dto) {
        return ResponseEntity.ok(playerProfileService.updateProfile(id, dto));
    }
}
