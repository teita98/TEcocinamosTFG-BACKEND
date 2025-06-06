package com.tecocinamos.service;

import com.tecocinamos.dto.AlergenoResponseDTO;

import java.util.List;

public interface AlergenoServiceI {
    AlergenoResponseDTO crear(String nombre);
    List<AlergenoResponseDTO> listar();
    void eliminar(Integer id);
}
