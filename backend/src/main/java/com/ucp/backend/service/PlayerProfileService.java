package com.ucp.backend.service;

import com.ucp.backend.dto.PlayerProfileDTO;
import com.ucp.backend.model.PlayerProfile;
import com.ucp.backend.repository.PlayerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerProfileService {
    
    private final PlayerProfileRepository playerProfileRepository;
    
    public List<PlayerProfileDTO> getAllProfiles() {
        return playerProfileRepository.findAllWithUserAndAssets().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public PlayerProfileDTO getProfileById(Long id) {
        PlayerProfile profile = playerProfileRepository.findByIdWithUserAndAssets(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return convertToDTO(profile);
    }
    
    public PlayerProfileDTO getProfileByUserId(Long userId) {
        PlayerProfile profile = playerProfileRepository.findByUserIdWithAssets(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return convertToDTO(profile);
    }
    
    @Transactional
    public PlayerProfileDTO updateProfile(Long id, PlayerProfileDTO dto) {
        PlayerProfile profile = playerProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        
        if (dto.getCharacterName() != null) {
            profile.setCharacterName(dto.getCharacterName());
        }
        if (dto.getLevel() != null) {
            profile.setLevel(dto.getLevel());
        }
        if (dto.getMoney() != null) {
            profile.setMoney(dto.getMoney());
        }
        if (dto.getPlayedHours() != null) {
            profile.setPlayedHours(dto.getPlayedHours());
        }
        
        profile = playerProfileRepository.save(profile);
        return convertToDTO(profile);
    }
    
    @Transactional
    public void updateLastLogin(Long userId) {
        PlayerProfile profile = playerProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        profile.setLastLogin(LocalDateTime.now());
        playerProfileRepository.save(profile);
    }
    
    private PlayerProfileDTO convertToDTO(PlayerProfile profile) {
        PlayerProfileDTO dto = new PlayerProfileDTO();
        dto.setId(profile.getId());
        dto.setUserId(profile.getUser().getId());
        dto.setUsername(profile.getUser().getUsername());
        dto.setCharacterName(profile.getCharacterName());
        dto.setLevel(profile.getLevel());
        dto.setMoney(profile.getMoney());
        dto.setPlayedHours(profile.getPlayedHours());
        dto.setLastLogin(profile.getLastLogin());
        dto.setHousesCount(profile.getHouses().size());
        dto.setVehiclesCount(profile.getVehicles().size());
        return dto;
    }
}
