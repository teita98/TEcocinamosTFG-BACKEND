package com.tecocinamos.service.impl;

import com.tecocinamos.dto.EstadoRequestDTO;
import com.tecocinamos.dto.EstadoResponseDTO;
import com.tecocinamos.exception.BadRequestException;
import com.tecocinamos.exception.NotFoundException;
import com.tecocinamos.model.Estado;
import com.tecocinamos.repository.EstadoRepository;
import com.tecocinamos.service.EstadoServiceI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadoServiceImpl implements EstadoServiceI {

    @Autowired
    private EstadoRepository estadoRepository;

    @Override
    public EstadoResponseDTO crearEstado(EstadoRequestDTO dto) {
        if (estadoRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new BadRequestException("Ya existe un estado con ese nombre");
        }
        Estado estado = Estado.builder()
                .nombre(dto.getNombre())
                .build();
        Estado guardado = estadoRepository.save(estado);
        return EstadoResponseDTO.builder()
                .id(guardado.getId())
                .nombre(guardado.getNombre())
                .build();
    }

    @Override
    public List<EstadoResponseDTO> listarEstados() {
        return estadoRepository.findAll().stream()
                .map(e -> EstadoResponseDTO.builder()
                        .id(e.getId())
                        .nombre(e.getNombre())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarEstado(Integer id) {
        if (!estadoRepository.existsById(id)) {
            throw new NotFoundException("Estado no encontrado con ID " + id);
        }
        estadoRepository.deleteById(id);
    }
}
