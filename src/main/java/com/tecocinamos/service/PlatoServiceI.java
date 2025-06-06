package com.tecocinamos.service;

import com.tecocinamos.dto.*;

import java.util.List;

public interface PlatoServiceI {

    PlatoResponseDTO crearPlato(PlatoRequestDTO dto);
    PlatoResponseDTO actualizarPlato(Integer id, PlatoRequestDTO dto);
    void eliminarPlato(Integer id); // eliminación real o lógica
    PlatoResponseDTO obtenerPlatoPorId(Integer id);
    List<PlatoListDTO> listarPlatos(); // listado resumido
    List<PlatoListDTO> buscarPorNombre(String nombre);
    List<PlatoListDTO> buscarPorCategoria(String categoria);
    List<IngredienteUsadoDTO> obtenerIngredientesPorPlato(Integer platoId);
    List<AlergenoResponseDTO> obtenerAlergenosPorPlato(Integer platoId);

}
