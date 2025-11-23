package com.ucp.backend.service;

import com.ucp.backend.dto.AdminNoteDTO;
import com.ucp.backend.model.AdminNote;
import com.ucp.backend.model.User;
import com.ucp.backend.repository.AdminNoteRepository;
import com.ucp.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminNoteService {
    
    private final AdminNoteRepository adminNoteRepository;
    private final UserRepository userRepository;
    
    public List<AdminNoteDTO> getAllNotes() {
        return adminNoteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public AdminNoteDTO getNoteById(Long id) {
        AdminNote note = adminNoteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));
        return convertToDTO(note);
    }
    
    public List<AdminNoteDTO> getNotesByUserId(Long userId) {
        return adminNoteRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public AdminNoteDTO createNote(AdminNoteDTO dto, String adminUsername) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        AdminNote note = new AdminNote();
        note.setUser(user);
        note.setNote(dto.getNote());
        note.setCreatedBy(adminUsername);
        
        note = adminNoteRepository.save(note);
        return convertToDTO(note);
    }
    
    @Transactional
    public void deleteNote(Long id) {
        adminNoteRepository.deleteById(id);
    }
    
    private AdminNoteDTO convertToDTO(AdminNote note) {
        AdminNoteDTO dto = new AdminNoteDTO();
        dto.setId(note.getId());
        dto.setUserId(note.getUser().getId());
        dto.setUsername(note.getUser().getUsername());
        dto.setNote(note.getNote());
        dto.setCreatedBy(note.getCreatedBy());
        dto.setCreatedAt(note.getCreatedAt());
        return dto;
    }
}
