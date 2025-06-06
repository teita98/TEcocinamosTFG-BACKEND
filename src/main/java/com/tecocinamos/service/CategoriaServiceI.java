package com.tecocinamos.service;

import com.tecocinamos.dto.CategoriaRequestDTO;
import com.tecocinamos.dto.CategoriaResponseDTO;

import java.util.List;

public interface CategoriaServiceI {

    CategoriaResponseDTO crearCategoria(CategoriaRequestDTO dto);
    List<CategoriaResponseDTO> listarCategorias();
    CategoriaResponseDTO obtenerCategoriaPorId(Integer id);
    CategoriaResponseDTO actualizarCategoria(Integer id, CategoriaRequestDTO dto);
    void eliminarCategoria(Integer id);
}
