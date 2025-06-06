package com.tecocinamos.service.impl;

import com.tecocinamos.dto.*;
import com.tecocinamos.exception.BadRequestException;
import com.tecocinamos.exception.NotFoundException;
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
        if (rolRepository.existsByNombreRolIgnoreCase(dto.getNombreRol())) {
            throw new BadRequestException("Ya existe un rol con ese nombre");
        }
        Rol rol = Rol.builder()
                .nombreRol(dto.getNombreRol())
                .build();
        Rol guardado = rolRepository.save(rol);
        return RolResponseDTO.builder()
                .id(guardado.getId())
                .nombreRol(guardado.getNombreRol())
                .build();
    }

    @Override
    public List<RolResponseDTO> listarRoles() {
        return rolRepository.findAll().stream()
                .map(r -> RolResponseDTO.builder()
                        .id(r.getId())
                        .nombreRol(r.getNombreRol())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public RolResponseDTO obtenerPorId(Integer id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rol no encontrado con ID " + id));
        return RolResponseDTO.builder()
                .id(rol.getId())
                .nombreRol(rol.getNombreRol())
                .build();
    }

    @Override
    public void eliminarRol(Integer id) {
        if (!rolRepository.existsById(id)) {
            throw new NotFoundException("Rol no encontrado con ID " + id);
        }
        rolRepository.deleteById(id);
    }

}
