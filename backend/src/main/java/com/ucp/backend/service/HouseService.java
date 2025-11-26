package com.ucp.backend.service;

import com.ucp.backend.dto.HouseDTO;
import com.ucp.backend.model.House;
import com.ucp.backend.model.PlayerProfile;
import com.ucp.backend.repository.HouseRepository;
import com.ucp.backend.repository.PlayerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HouseService {
    
    private final HouseRepository houseRepository;
    private final PlayerProfileRepository playerProfileRepository;
    
    public List<HouseDTO> getAllHouses() {
        return houseRepository.findAllWithOwnerAndUser().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public HouseDTO getHouseById(Long id) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("House not found"));
        return convertToDTO(house);
    }
    
    public List<HouseDTO> getHousesByOwnerId(Long ownerId) {
        return houseRepository.findByOwnerIdWithUser(ownerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public HouseDTO createHouse(HouseDTO dto) {
        PlayerProfile owner = playerProfileRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        
        House house = new House();
        house.setOwner(owner);
        house.setAddress(dto.getAddress());
        house.setPrice(dto.getPrice());
        house.setInteriorId(dto.getInteriorId());
        house.setLocked(dto.getLocked() != null ? dto.getLocked() : false);
        
        house = houseRepository.save(house);
        return convertToDTO(house);
    }
    
    @Transactional
    public HouseDTO updateHouse(Long id, HouseDTO dto) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("House not found"));
        
        if (dto.getAddress() != null) {
            house.setAddress(dto.getAddress());
        }
        if (dto.getLocked() != null) {
            house.setLocked(dto.getLocked());
        }
        
        house = houseRepository.save(house);
        return convertToDTO(house);
    }
    
    @Transactional
    public void deleteHouse(Long id) {
        houseRepository.deleteById(id);
    }
    
    private HouseDTO convertToDTO(House house) {
        HouseDTO dto = new HouseDTO();
        dto.setId(house.getId());
        dto.setOwnerId(house.getOwner().getId());
        dto.setOwnerName(house.getOwner().getUser().getUsername());
        dto.setAddress(house.getAddress());
        dto.setPrice(house.getPrice());
        dto.setInteriorId(house.getInteriorId());
        dto.setLocked(house.getLocked());
        dto.setPurchasedAt(house.getPurchasedAt());
        return dto;
    }
}
