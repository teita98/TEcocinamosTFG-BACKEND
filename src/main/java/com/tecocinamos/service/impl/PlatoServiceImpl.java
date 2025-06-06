package com.tecocinamos.service.impl;

import com.tecocinamos.dto.*;
import com.tecocinamos.exception.BadRequestException;
import com.tecocinamos.exception.NotFoundException;
import com.tecocinamos.model.*;
import com.tecocinamos.repository.*;
import com.tecocinamos.service.PlatoServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlatoServiceImpl implements PlatoServiceI {

    @Autowired
    private PlatoRepository platoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private PlatoIngredienteRepository platoIngredienteRepository;

    @Autowired
    private IngredienteAlergenoRepository ingredienteAlergenoRepository;

    @Override
    public PlatoResponseDTO crearPlato(PlatoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada con ID " + dto.getCategoriaId()));

        Plato plato = new Plato();
        plato.setNombrePlato(dto.getNombrePlato());
        plato.setCantidad(dto.getCantidad());
        plato.setPrecio(dto.getPrecio());
        plato.setStock(dto.getStock());
        plato.setPreparacionCasa(dto.getPreparacionCasa());
        plato.setRecomendaciones(dto.getRecomendaciones());
        plato.setImageBaseName(dto.getImageBaseName());
        plato.setCategoria(categoria);
        plato.setFechaActualizacion(LocalDateTime.now());

        Plato guardado = platoRepository.save(plato);

        // Guardar ingredientes
        if (dto.getIngredientesUsados() != null) {
            for (IngredienteDetalleDTO usado : dto.getIngredientesUsados()) {
                Ingrediente ing = ingredienteRepository.findById(usado.getIngredienteId())
                        .orElseThrow(() -> new NotFoundException("Ingrediente no encontrado con ID " + usado.getIngredienteId()));

                ingredienteRepository.save(ing);

                PlatoIngrediente pi = PlatoIngrediente.builder()
                        .plato(guardado)
                        .ingrediente(ing)
                        .cantidadUsada(usado.getCantidadUsada())
                        .unidad(usado.getUnidad())
                        .build();
                platoIngredienteRepository.save(pi);
            }
        }

        return convertirAResponse(guardado);
    }

    @Override
    public PlatoResponseDTO actualizarPlato(Integer id, PlatoRequestDTO dto) {
        Plato plato = platoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plato no encontrado con ID " + id));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada con ID " + dto.getCategoriaId()));

        plato.setNombrePlato(dto.getNombrePlato());
        plato.setCantidad(dto.getCantidad());
        plato.setPrecio(dto.getPrecio());
        plato.setStock(dto.getStock());
        plato.setPreparacionCasa(dto.getPreparacionCasa());
        plato.setRecomendaciones(dto.getRecomendaciones());
        plato.setImageBaseName(dto.getImageBaseName());
        plato.setCategoria(categoria);
        plato.setFechaActualizacion(LocalDateTime.now());

        // Eliminar ingredientes anteriores y restaurar stock de ingrediente previo
        List<PlatoIngrediente> antiguos = platoIngredienteRepository.findByPlato(plato);
        for (PlatoIngrediente pi : antiguos) {
            Ingrediente ingPrev = pi.getIngrediente();
            ingredienteRepository.save(ingPrev);
        }
        platoIngredienteRepository.deleteAll(antiguos);

        Plato actualizado = platoRepository.save(plato);

        // Guardar nuevos ingredientes
        if (dto.getIngredientesUsados() != null) {
            for (IngredienteDetalleDTO usado : dto.getIngredientesUsados()) {
                Ingrediente ing = ingredienteRepository.findById(usado.getIngredienteId())
                        .orElseThrow(() -> new NotFoundException("Ingrediente no encontrado con ID " + usado.getIngredienteId()));

                PlatoIngrediente pi = PlatoIngrediente.builder()
                        .plato(actualizado)
                        .ingrediente(ing)
                        .cantidadUsada(usado.getCantidadUsada())
                        .unidad(usado.getUnidad())
                        .build();
                platoIngredienteRepository.save(pi);
            }
        }

        return convertirAResponse(actualizado);
    }

    @Override
    public void eliminarPlato(Integer id) {
        if (!platoRepository.existsById(id)) {
            throw new NotFoundException("Plato no encontrado con ID " + id);
        }

        Plato plato = platoRepository.findById(id).get();
        List<PlatoIngrediente> usados = platoIngredienteRepository.findByPlato(plato);
        for (PlatoIngrediente pi : usados) {
            Ingrediente ing = pi.getIngrediente();
            ingredienteRepository.save(ing);
        }
        platoIngredienteRepository.deleteAll(usados);
        platoRepository.deleteById(id);
    }

    @Override
    public PlatoResponseDTO obtenerPlatoPorId(Integer id) {
        Plato plato = platoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plato no encontrado con ID " + id));
        return convertirAResponse(plato);
    }

    @Override
    public Page<PlatoListDTO> listarPlatos(Pageable pageable) {
        return platoRepository.findAll(pageable)
                .map(this::convertirAListDTO);
    }

    @Override
    public Page<PlatoListDTO> buscarPorCategoria(String categoria, Pageable pageable) {
        return platoRepository.findByCategoriaNombreIgnoreCase(categoria, pageable)
                .map(this::convertirAListDTO);
    }

    @Override
    public Page<PlatoListDTO> buscarPorNombre(String nombre, Pageable pageable) {
        return platoRepository.findByNombrePlatoContainingIgnoreCase(nombre, pageable)
                .map(this::convertirAListDTO);
    }

    @Override
    public List<IngredienteDetalleDTO> obtenerIngredientesPorPlato(Integer platoId) {
        Plato plato = platoRepository.findById(platoId)
                .orElseThrow(() -> new NotFoundException("Plato no encontrado con ID " + platoId));
        return platoIngredienteRepository.findByPlato(plato).stream()
                .map(pi -> IngredienteDetalleDTO.builder()
                        .ingredienteId(pi.getIngrediente().getId())
                        .nombreIngrediente(pi.getIngrediente().getNombre())
                        .cantidadUsada(pi.getCantidadUsada())
                        .unidad(pi.getUnidad())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<AlergenoResponseDTO> obtenerAlergenosPorPlato(Integer platoId) {
        Plato plato = platoRepository.findById(platoId)
                .orElseThrow(() -> new NotFoundException("Plato no encontrado con ID " + platoId));

        // Recorre cada ingrediente del plato y recopila sus alérgenos
        Set<Alergeno> alSet = new HashSet<>();
        List<PlatoIngrediente> usados = platoIngredienteRepository.findByPlato(plato);
        for (PlatoIngrediente pi : usados) {
            List<IngredienteAlergeno> ingsAll = ingredienteAlergenoRepository.findByIngredienteId(pi.getIngrediente().getId());
            for (IngredienteAlergeno ia : ingsAll) {
                alSet.add(ia.getAlergeno());
            }
        }
        // Convertir set a lista de DTOs
        return alSet.stream()
                .map(a -> AlergenoResponseDTO.builder()
                        .id(a.getId())
                        .nombre(a.getNombre())
                        .build())
                .collect(Collectors.toList());
    }

    private PlatoListDTO convertirAListDTO(Plato plato) {
        return PlatoListDTO.builder()
                .id(plato.getId())
                .nombrePlato(plato.getNombrePlato())
                .precio(plato.getPrecio())
                .categoria(plato.getCategoria().getNombre())
                .stock(plato.getStock())
                .imageBaseName(plato.getImageBaseName())
                .build();
    }

    private PlatoResponseDTO convertirAResponse(Plato plato) {
        List<IngredienteDetalleDTO> ingredientes = plato.getIngredientes().stream()
                .map(pi -> IngredienteDetalleDTO.builder()
                        .ingredienteId(pi.getIngrediente().getId())
                        .nombreIngrediente(pi.getIngrediente().getNombre())
                        .cantidadUsada(pi.getCantidadUsada())
                        .unidad(pi.getUnidad())
                        .build())
                .collect(Collectors.toList());

        return PlatoResponseDTO.builder()
                .id(plato.getId())
                .nombrePlato(plato.getNombrePlato())
                .cantidad(plato.getCantidad())
                .precio(plato.getPrecio())
                .stock(plato.getStock())
                .preparacionCasa(plato.getPreparacionCasa())
                .recomendaciones(plato.getRecomendaciones())
                .categoriaNombre(plato.getCategoria().getNombre())
                .ingredientes(ingredientes)
                .imageBaseName(plato.getImageBaseName()) // ← lo que acabamos de añadir
                .build();
    }

}
