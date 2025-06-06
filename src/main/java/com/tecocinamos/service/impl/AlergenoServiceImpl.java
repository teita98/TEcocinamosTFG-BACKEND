package com.tecocinamos.service.impl;

import com.tecocinamos.dto.AlergenoResponseDTO;
import com.tecocinamos.exception.BadRequestException;
import com.tecocinamos.exception.NotFoundException;
import com.tecocinamos.model.Alergeno;
import com.tecocinamos.repository.AlergenoRepository;
import com.tecocinamos.service.AlergenoServiceI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlergenoServiceImpl implements AlergenoServiceI {

    @Autowired
    private AlergenoRepository alergenoRepository;

    @Override
    public AlergenoResponseDTO crear(String nombre) {
        if (alergenoRepository.existsByNombreIgnoreCase(nombre)) {
            throw new BadRequestException("Ya existe un alérgeno con ese nombre");
        }
        Alergeno alergeno = Alergeno.builder().nombre(nombre).build();
        Alergeno guardado = alergenoRepository.save(alergeno);
        return AlergenoResponseDTO.builder()
                .id(guardado.getId())
                .nombre(guardado.getNombre())
                .build();
    }

    @Override
    public List<AlergenoResponseDTO> listar() {
        return alergenoRepository.findAll().stream()
                .map(a -> AlergenoResponseDTO.builder()
                        .id(a.getId())
                        .nombre(a.getNombre())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Integer id) {
        if (!alergenoRepository.existsById(id)) {
            throw new NotFoundException("Alérgeno no encontrado con ID " + id);
        }
        alergenoRepository.deleteById(id);
    }
}