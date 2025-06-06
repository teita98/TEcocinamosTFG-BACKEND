package com.tecocinamos.service.impl;

import com.tecocinamos.dto.CategoriaRequestDTO;
import com.tecocinamos.dto.CategoriaResponseDTO;
import com.tecocinamos.exception.BadRequestException;
import com.tecocinamos.exception.NotFoundException;
import com.tecocinamos.model.Categoria;
import com.tecocinamos.repository.CategoriaRepository;
import com.tecocinamos.service.CategoriaServiceI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaServiceImpl implements CategoriaServiceI {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public CategoriaResponseDTO crearCategoria(CategoriaRequestDTO dto) {
        if (categoriaRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new BadRequestException("Ya existe una categoría con ese nombre");
        }
        Categoria categoria = Categoria.builder()
                .nombre(dto.getNombre())
                .build();
        Categoria guardada = categoriaRepository.save(categoria);
        return CategoriaResponseDTO.builder()
                .id(guardada.getId())
                .nombre(guardada.getNombre())
                .build();
    }

    @Override
    public List<CategoriaResponseDTO> listarCategorias() {
        return categoriaRepository.findAll().stream()
                .map(c -> CategoriaResponseDTO.builder()
                        .id(c.getId())
                        .nombre(c.getNombre())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaResponseDTO obtenerCategoriaPorId(Integer id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada con ID " + id));
        return CategoriaResponseDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .build();
    }

    @Override
    public CategoriaResponseDTO actualizarCategoria(Integer id, CategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada con ID " + id));
        if (!categoria.getNombre().equalsIgnoreCase(dto.getNombre())
                && categoriaRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new BadRequestException("Ya existe una categoría con ese nombre");
        }
        categoria.setNombre(dto.getNombre());
        Categoria actualizada = categoriaRepository.save(categoria);
        return CategoriaResponseDTO.builder()
                .id(actualizada.getId())
                .nombre(actualizada.getNombre())
                .build();
    }

    @Override
    public void eliminarCategoria(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new NotFoundException("Categoría no encontrada con ID " + id);
        }
        categoriaRepository.deleteById(id);
    }
}
