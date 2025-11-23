package com.ucp.backend.controller;

import com.ucp.backend.dto.WarningDTO;
import com.ucp.backend.service.WarningService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/warnings")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'LEAD_ADMIN')")
public class WarningController {
    
    private final WarningService warningService;
    
    @GetMapping
    public ResponseEntity<List<WarningDTO>> getAllWarnings() {
        return ResponseEntity.ok(warningService.getAllWarnings());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<WarningDTO> getWarningById(@PathVariable Long id) {
        return ResponseEntity.ok(warningService.getWarningById(id));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WarningDTO>> getWarningsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(warningService.getWarningsByUserId(userId));
    }
    
    @PostMapping
    public ResponseEntity<WarningDTO> createWarning(@Valid @RequestBody WarningDTO dto, Authentication authentication) {
        return ResponseEntity.ok(warningService.createWarning(dto, authentication.getName()));
    }
    
    @PutMapping("/{id}/acknowledge")
    public ResponseEntity<WarningDTO> acknowledgeWarning(@PathVariable Long id) {
        return ResponseEntity.ok(warningService.acknowledgeWarning(id));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('LEAD_ADMIN')")
    public ResponseEntity<Void> deleteWarning(@PathVariable Long id) {
        warningService.deleteWarning(id);
        return ResponseEntity.ok().build();
    }
}
