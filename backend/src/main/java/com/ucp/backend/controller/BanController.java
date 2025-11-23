package com.ucp.backend.controller;

import com.ucp.backend.dto.BanDTO;
import com.ucp.backend.service.BanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/bans")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'LEAD_ADMIN')")
public class BanController {
    
    private final BanService banService;
    
    @GetMapping
    public ResponseEntity<List<BanDTO>> getAllBans() {
        return ResponseEntity.ok(banService.getAllBans());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BanDTO> getBanById(@PathVariable Long id) {
        return ResponseEntity.ok(banService.getBanById(id));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BanDTO>> getBansByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(banService.getBansByUserId(userId));
    }
    
    @PostMapping
    public ResponseEntity<BanDTO> createBan(@Valid @RequestBody BanDTO dto, Authentication authentication) {
        return ResponseEntity.ok(banService.createBan(dto, authentication.getName()));
    }
    
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<BanDTO> deactivateBan(@PathVariable Long id) {
        return ResponseEntity.ok(banService.deactivateBan(id));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('LEAD_ADMIN')")
    public ResponseEntity<Void> deleteBan(@PathVariable Long id) {
        banService.deleteBan(id);
        return ResponseEntity.ok().build();
    }
}
