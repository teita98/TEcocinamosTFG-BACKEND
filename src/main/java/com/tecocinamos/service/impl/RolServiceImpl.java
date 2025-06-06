package com.tecocinamos.service.impl;

import com.tecocinamos.dto.*;
import com.tecocinamos.model.Rol;
import com.tecocinamos.repository.RolRepository;
import com.tecocinamos.service.RolServiceI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RolServiceImpl implements RolServiceI {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public RolResponseDTO crearRol(RolRequestDTO dto) {
        Rol rol = Rol.builder()
                .nombreRol(dto.getNombreRol())
                .build();
        return mapToDTO(rolRepository.save(rol));
    }

    @Override
    public List<RolResponseDTO> listarRoles() {
        return rolRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RolResponseDTO obtenerPorId(Integer id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));
        return mapToDTO(rol);
    }

    @Override
    public void eliminarRol(Integer id) {
        if (!rolRepository.existsById(id)) {
            throw new EntityNotFoundException("Rol no encontrado");
        }
        rolRepository.deleteById(id);
    }

    private RolResponseDTO mapToDTO(Rol rol) {
        return RolResponseDTO.builder()
                .id(rol.getId())
                .nombreRol(rol.getNombreRol())
                .build();
    }
}
