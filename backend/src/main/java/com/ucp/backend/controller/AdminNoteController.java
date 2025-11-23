package com.ucp.backend.controller;

import com.ucp.backend.dto.AdminNoteDTO;
import com.ucp.backend.service.AdminNoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/notes")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'LEAD_ADMIN')")
public class AdminNoteController {
    
    private final AdminNoteService adminNoteService;
    
    @GetMapping
    public ResponseEntity<List<AdminNoteDTO>> getAllNotes() {
        return ResponseEntity.ok(adminNoteService.getAllNotes());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AdminNoteDTO> getNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(adminNoteService.getNoteById(id));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AdminNoteDTO>> getNotesByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(adminNoteService.getNotesByUserId(userId));
    }
    
    @PostMapping
    public ResponseEntity<AdminNoteDTO> createNote(@Valid @RequestBody AdminNoteDTO dto, Authentication authentication) {
        return ResponseEntity.ok(adminNoteService.createNote(dto, authentication.getName()));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('LEAD_ADMIN')")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        adminNoteService.deleteNote(id);
        return ResponseEntity.ok().build();
    }
}
