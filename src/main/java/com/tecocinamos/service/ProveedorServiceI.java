package com.tecocinamos.service;

import com.tecocinamos.dto.ProveedorRequestDTO;
import com.tecocinamos.dto.ProveedorResponseDTO;

import java.util.List;

public interface ProveedorServiceI {
    ProveedorResponseDTO crearProveedor(ProveedorRequestDTO dto);
    List<ProveedorResponseDTO> obtenerTodos();
    ProveedorResponseDTO obtenerPorId(Integer id);
    ProveedorResponseDTO actualizarProveedor(Integer id, ProveedorRequestDTO dto);
    void eliminarProveedor(Integer id);
}
