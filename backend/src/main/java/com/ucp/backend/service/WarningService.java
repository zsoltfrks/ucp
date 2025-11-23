package com.ucp.backend.service;

import com.ucp.backend.dto.WarningDTO;
import com.ucp.backend.model.User;
import com.ucp.backend.model.Warning;
import com.ucp.backend.repository.UserRepository;
import com.ucp.backend.repository.WarningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarningService {
    
    private final WarningRepository warningRepository;
    private final UserRepository userRepository;
    
    public List<WarningDTO> getAllWarnings() {
        return warningRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public WarningDTO getWarningById(Long id) {
        Warning warning = warningRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warning not found"));
        return convertToDTO(warning);
    }
    
    public List<WarningDTO> getWarningsByUserId(Long userId) {
        return warningRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public WarningDTO createWarning(WarningDTO dto, String adminUsername) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Warning warning = new Warning();
        warning.setUser(user);
        warning.setReason(dto.getReason());
        warning.setWarnedBy(adminUsername);
        
        warning = warningRepository.save(warning);
        return convertToDTO(warning);
    }
    
    @Transactional
    public WarningDTO acknowledgeWarning(Long id) {
        Warning warning = warningRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warning not found"));
        warning.setAcknowledged(true);
        warning = warningRepository.save(warning);
        return convertToDTO(warning);
    }
    
    @Transactional
    public void deleteWarning(Long id) {
        warningRepository.deleteById(id);
    }
    
    private WarningDTO convertToDTO(Warning warning) {
        WarningDTO dto = new WarningDTO();
        dto.setId(warning.getId());
        dto.setUserId(warning.getUser().getId());
        dto.setUsername(warning.getUser().getUsername());
        dto.setReason(warning.getReason());
        dto.setWarnedBy(warning.getWarnedBy());
        dto.setWarnedAt(warning.getWarnedAt());
        dto.setAcknowledged(warning.getAcknowledged());
        return dto;
    }
}
