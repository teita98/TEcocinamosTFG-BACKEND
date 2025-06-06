package com.tecocinamos.service.impl;

import com.tecocinamos.dto.AlergenoResponseDTO;
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
        Alergeno alergeno = Alergeno.builder().nombre(nombre).build();
        return map(alergenoRepository.save(alergeno));
    }

    @Override
    public List<AlergenoResponseDTO> listar() {
        return alergenoRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Integer id) {
        if (!alergenoRepository.existsById(id)) {
            throw new EntityNotFoundException("Alergeno no encontrado");
        }
        alergenoRepository.deleteById(id);
    }

    private AlergenoResponseDTO map(Alergeno a) {
        return AlergenoResponseDTO.builder()
                .id(a.getId())
                .nombre(a.getNombre())
                .build();
    }
}
