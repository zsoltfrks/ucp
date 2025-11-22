package com.ucp.backend.controller;

import com.ucp.backend.dto.HouseDTO;
import com.ucp.backend.service.HouseService;
import com.ucp.backend.service.PlayerProfileService;
import com.ucp.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/houses")
@RequiredArgsConstructor
public class HouseController {
    
    private final HouseService houseService;
    private final UserService userService;
    private final PlayerProfileService playerProfileService;
    
    @GetMapping("/me")
    public ResponseEntity<List<HouseDTO>> getMyHouses(Authentication authentication) {
        Long userId = userService.getUserByUsername(authentication.getName()).getId();
        Long profileId = playerProfileService.getProfileByUserId(userId).getId();
        return ResponseEntity.ok(houseService.getHousesByOwnerId(profileId));
    }
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LEAD_ADMIN')")
    public ResponseEntity<List<HouseDTO>> getAllHouses() {
        return ResponseEntity.ok(houseService.getAllHouses());
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LEAD_ADMIN')")
    public ResponseEntity<HouseDTO> getHouseById(@PathVariable Long id) {
        return ResponseEntity.ok(houseService.getHouseById(id));
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'LEAD_ADMIN')")
    public ResponseEntity<HouseDTO> createHouse(@RequestBody HouseDTO dto) {
        return ResponseEntity.ok(houseService.createHouse(dto));
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LEAD_ADMIN')")
    public ResponseEntity<HouseDTO> updateHouse(@PathVariable Long id, @RequestBody HouseDTO dto) {
        return ResponseEntity.ok(houseService.updateHouse(id, dto));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'LEAD_ADMIN')")
    public ResponseEntity<Void> deleteHouse(@PathVariable Long id) {
        houseService.deleteHouse(id);
        return ResponseEntity.ok().build();
    }
}
