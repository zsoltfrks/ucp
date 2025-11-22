package com.ucp.backend.service;

import com.ucp.backend.dto.BanDTO;
import com.ucp.backend.model.Ban;
import com.ucp.backend.model.User;
import com.ucp.backend.repository.BanRepository;
import com.ucp.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BanService {
    
    private final BanRepository banRepository;
    private final UserRepository userRepository;
    
    public List<BanDTO> getAllBans() {
        return banRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public BanDTO getBanById(Long id) {
        Ban ban = banRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ban not found"));
        return convertToDTO(ban);
    }
    
    public List<BanDTO> getBansByUserId(Long userId) {
        return banRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public BanDTO createBan(BanDTO dto, String adminUsername) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Ban ban = new Ban();
        ban.setUser(user);
        ban.setReason(dto.getReason());
        ban.setBannedBy(adminUsername);
        ban.setExpiresAt(dto.getExpiresAt());
        
        ban = banRepository.save(ban);
        return convertToDTO(ban);
    }
    
    @Transactional
    public BanDTO deactivateBan(Long id) {
        Ban ban = banRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ban not found"));
        ban.setActive(false);
        ban = banRepository.save(ban);
        return convertToDTO(ban);
    }
    
    @Transactional
    public void deleteBan(Long id) {
        banRepository.deleteById(id);
    }
    
    private BanDTO convertToDTO(Ban ban) {
        BanDTO dto = new BanDTO();
        dto.setId(ban.getId());
        dto.setUserId(ban.getUser().getId());
        dto.setUsername(ban.getUser().getUsername());
        dto.setReason(ban.getReason());
        dto.setBannedBy(ban.getBannedBy());
        dto.setBannedAt(ban.getBannedAt());
        dto.setExpiresAt(ban.getExpiresAt());
        dto.setActive(ban.getActive());
        return dto;
    }
}
