package com.ucp.backend.service;

import com.ucp.backend.dto.VehicleDTO;
import com.ucp.backend.model.PlayerProfile;
import com.ucp.backend.model.Vehicle;
import com.ucp.backend.repository.PlayerProfileRepository;
import com.ucp.backend.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleService {
    
    private final VehicleRepository vehicleRepository;
    private final PlayerProfileRepository playerProfileRepository;
    
    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepository.findAllWithOwnerAndUser().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public VehicleDTO getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        return convertToDTO(vehicle);
    }
    
    public List<VehicleDTO> getVehiclesByOwnerId(Long ownerId) {
        return vehicleRepository.findByOwnerIdWithUser(ownerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public VehicleDTO createVehicle(VehicleDTO dto) {
        PlayerProfile owner = playerProfileRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        
        Vehicle vehicle = new Vehicle();
        vehicle.setOwner(owner);
        vehicle.setModel(dto.getModel());
        vehicle.setColor(dto.getColor());
        vehicle.setPlateNumber(dto.getPlateNumber());
        vehicle.setPrice(dto.getPrice());
        vehicle.setHealth(dto.getHealth() != null ? dto.getHealth() : 1000);
        vehicle.setImpounded(dto.getImpounded() != null ? dto.getImpounded() : false);
        
        vehicle = vehicleRepository.save(vehicle);
        return convertToDTO(vehicle);
    }
    
    @Transactional
    public VehicleDTO updateVehicle(Long id, VehicleDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        
        if (dto.getColor() != null) {
            vehicle.setColor(dto.getColor());
        }
        if (dto.getHealth() != null) {
            vehicle.setHealth(dto.getHealth());
        }
        if (dto.getImpounded() != null) {
            vehicle.setImpounded(dto.getImpounded());
        }
        
        vehicle = vehicleRepository.save(vehicle);
        return convertToDTO(vehicle);
    }
    
    @Transactional
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }
    
    private VehicleDTO convertToDTO(Vehicle vehicle) {
        VehicleDTO dto = new VehicleDTO();
        dto.setId(vehicle.getId());
        dto.setOwnerId(vehicle.getOwner().getId());
        dto.setOwnerName(vehicle.getOwner().getUser().getUsername());
        dto.setModel(vehicle.getModel());
        dto.setColor(vehicle.getColor());
        dto.setPlateNumber(vehicle.getPlateNumber());
        dto.setPrice(vehicle.getPrice());
        dto.setHealth(vehicle.getHealth());
        dto.setImpounded(vehicle.getImpounded());
        dto.setPurchasedAt(vehicle.getPurchasedAt());
        return dto;
    }
}
