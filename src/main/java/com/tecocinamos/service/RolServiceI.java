package com.tecocinamos.service;

import com.tecocinamos.dto.RolRequestDTO;
import com.tecocinamos.dto.RolResponseDTO;

import java.util.List;

public interface RolServiceI {
    RolResponseDTO crearRol(RolRequestDTO dto);
    List<RolResponseDTO> listarRoles();
    RolResponseDTO obtenerPorId(Integer id);
    void eliminarRol(Integer id);
}
