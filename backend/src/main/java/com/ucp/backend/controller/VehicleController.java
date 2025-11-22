package com.ucp.backend.controller;

import com.ucp.backend.dto.VehicleDTO;
import com.ucp.backend.service.PlayerProfileService;
import com.ucp.backend.service.UserService;
import com.ucp.backend.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    
    private final VehicleService vehicleService;
    private final UserService userService;
    private final PlayerProfileService playerProfileService;
    
    @GetMapping("/me")
    public ResponseEntity<List<VehicleDTO>> getMyVehicles(Authentication authentication) {
        Long userId = userService.getUserByUsername(authentication.getName()).getId();
        Long profileId = playerProfileService.getProfileByUserId(userId).getId();
        return ResponseEntity.ok(vehicleService.getVehiclesByOwnerId(profileId));
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LEAD_ADMIN')")
    public ResponseEntity<List<VehicleDTO>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LEAD_ADMIN')")
    public ResponseEntity<VehicleDTO> getVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getVehicleById(id));
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LEAD_ADMIN')")
    public ResponseEntity<VehicleDTO> createVehicle(@RequestBody VehicleDTO dto) {
        return ResponseEntity.ok(vehicleService.createVehicle(dto));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LEAD_ADMIN')")
    public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable Long id, @RequestBody VehicleDTO dto) {
        return ResponseEntity.ok(vehicleService.updateVehicle(id, dto));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LEAD_ADMIN')")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return ResponseEntity.ok().build();
    }
}
