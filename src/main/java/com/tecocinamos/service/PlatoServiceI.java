package com.tecocinamos.service;

import com.tecocinamos.dto.PlatoListDTO;
import com.tecocinamos.dto.PlatoRequestDTO;
import com.tecocinamos.dto.PlatoResponseDTO;
import com.tecocinamos.dto.AlergenoResponseDTO;
import com.tecocinamos.dto.IngredienteDetalleDTO;
import org.springframework.data.domain.*;

import java.util.List;

public interface PlatoServiceI {
    PlatoResponseDTO crearPlato(PlatoRequestDTO dto);
    PlatoResponseDTO actualizarPlato(Integer id, PlatoRequestDTO dto);
    void eliminarPlato(Integer id);
    PlatoResponseDTO obtenerPlatoPorId(Integer id);
    Page<PlatoListDTO> listarPlatos(Pageable pageable);
    Page<PlatoListDTO> buscarPorCategoria(String categoria, Pageable pageable);
    Page<PlatoListDTO> buscarPorNombre(String nombre, Pageable pageable);
    List<IngredienteDetalleDTO> obtenerIngredientesPorPlato(Integer platoId);
    List<AlergenoResponseDTO> obtenerAlergenosPorPlato(Integer platoId);
}
