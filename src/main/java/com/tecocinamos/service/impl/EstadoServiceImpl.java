package com.tecocinamos.service.impl;

import com.tecocinamos.dto.EstadoRequestDTO;
import com.tecocinamos.dto.EstadoResponseDTO;
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
        Estado estado = Estado.builder()
                .nombre(dto.getNombre())
                .build();

        Estado guardado = estadoRepository.save(estado);
        return mapToDTO(guardado);
    }

    @Override
    public List<EstadoResponseDTO> listarEstados() {
        return estadoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarEstado(Integer id) {
        if (!estadoRepository.existsById(id)) {
            throw new EntityNotFoundException("Estado no encontrado");
        }
        estadoRepository.deleteById(id);
    }

    private EstadoResponseDTO mapToDTO(Estado estado) {
        return EstadoResponseDTO.builder()
                .id(estado.getId())
                .nombre(estado.getNombre())
                .build();
    }
}
