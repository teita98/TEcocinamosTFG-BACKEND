package com.tecocinamos.service;

import com.tecocinamos.dto.EstadoRequestDTO;
import com.tecocinamos.dto.EstadoResponseDTO;

import java.util.List;

public interface EstadoServiceI {
    EstadoResponseDTO crearEstado(EstadoRequestDTO dto);
    List<EstadoResponseDTO> listarEstados();
    void eliminarEstado(Integer id);
}
