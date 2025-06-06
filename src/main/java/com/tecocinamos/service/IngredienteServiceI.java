package com.tecocinamos.service;

import com.tecocinamos.dto.IngredienteRequestDTO;
import com.tecocinamos.dto.IngredienteResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IngredienteServiceI {
    IngredienteResponseDTO crearIngrediente(IngredienteRequestDTO dto);
    Page<IngredienteResponseDTO> listarIngredientes(Pageable pageable);
    IngredienteResponseDTO obtenerIngredientePorId(Integer id);
    Page<IngredienteResponseDTO> listarPorProveedor(Integer proveedorId, Pageable pageable);
    IngredienteResponseDTO actualizarIngrediente(Integer id, IngredienteRequestDTO dto);
    void eliminarIngrediente(Integer id);
    IngredienteResponseDTO agregarAlergenoIngrediente(Integer ingredienteId, Integer alergenoId);

}
