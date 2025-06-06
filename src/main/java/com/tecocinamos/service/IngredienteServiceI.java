package com.tecocinamos.service;

import com.tecocinamos.dto.IngredienteRequestDTO;
import com.tecocinamos.dto.IngredienteResponseDTO;

import java.util.List;

public interface IngredienteServiceI {
    IngredienteResponseDTO crearIngrediente(IngredienteRequestDTO dto);
    List<IngredienteResponseDTO> listarIngredientes();
    IngredienteResponseDTO obtenerIngredientePorId(Integer id);
    IngredienteResponseDTO actualizarIngrediente(Integer id, IngredienteRequestDTO dto);
    void eliminarIngrediente(Integer id);
}
