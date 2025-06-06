package com.tecocinamos.service.impl;

import com.tecocinamos.dto.CategoriaRequestDTO;
import com.tecocinamos.dto.CategoriaResponseDTO;
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
        Categoria categoria = Categoria.builder()
                .nombre(dto.getNombre())
                .build();

        Categoria guardada = categoriaRepository.save(categoria);
        return mapToDTO(guardada);
    }

    @Override
    public List<CategoriaResponseDTO> listarCategorias() {
        return categoriaRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaResponseDTO obtenerCategoriaPorId(Integer id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
        return mapToDTO(categoria);
    }

    @Override
    public CategoriaResponseDTO actualizarCategoria(Integer id, CategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));

        categoria.setNombre(dto.getNombre());

        Categoria actualizada = categoriaRepository.save(categoria);
        return mapToDTO(actualizada);
    }

    @Override
    public void eliminarCategoria(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new EntityNotFoundException("Categoría no encontrada");
        }
        categoriaRepository.deleteById(id);
    }

    // Método auxiliar
    private CategoriaResponseDTO mapToDTO(Categoria categoria) {
        return CategoriaResponseDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .build();
    }
}
