package com.tecocinamos.service.impl;

import com.tecocinamos.dto.*;
import com.tecocinamos.model.*;
import com.tecocinamos.repository.*;
import com.tecocinamos.service.PlatoServiceI;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
    private AlergenoRepository alergenoRepository;

    @Autowired
    private PlatoAlergenoRepository platoAlergenoRepository;

    @Override
    public PlatoResponseDTO crearPlato(PlatoRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));

        Plato plato = new Plato();
        plato.setNombrePlato(dto.getNombrePlato());
        plato.setCantidad(dto.getCantidad());
        plato.setPrecio(dto.getPrecio());
        plato.setStock(dto.getStock());
        plato.setPreparacionCasa(dto.getPreparacionCasa());
        plato.setRecomendaciones(dto.getRecomendaciones());
        plato.setCategoria(categoria);
        plato.setFechaActualizacion(LocalDateTime.now());

        Plato guardado = platoRepository.save(plato);

        // Guardar ingredientes
        if (dto.getIngredientesUsados() != null) {
            for (var usado : dto.getIngredientesUsados()) {
                Ingrediente ing = ingredienteRepository.findById(usado.getIngredienteId())
                        .orElseThrow(() -> new EntityNotFoundException("Ingrediente no encontrado"));

                PlatoIngrediente pi = PlatoIngrediente.builder()
                        .plato(guardado)
                        .ingrediente(ing)
                        .cantidadUsada(usado.getCantidadUsada())
                        .unidad(usado.getUnidad())
                        .build();

                platoIngredienteRepository.save(pi);
            }
        }

        // Guardar alérgenos
        if (dto.getAlergenosIds() != null) {
            for (Integer idAlergeno : dto.getAlergenosIds()) {
                Alergeno alergeno = alergenoRepository.findById(idAlergeno)
                        .orElseThrow(() -> new EntityNotFoundException("Alergeno no encontrado"));

                PlatoAlergeno pa = PlatoAlergeno.builder()
                        .plato(guardado)
                        .alergeno(alergeno)
                        .build();

                platoAlergenoRepository.save(pa);
            }
        }


        return convertirAResponse(guardado);
    }



    @Override
    public PlatoResponseDTO actualizarPlato(Integer id, PlatoRequestDTO dto) {
        Plato plato = platoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plato no encontrado"));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));

        plato.setNombrePlato(dto.getNombrePlato());
        plato.setCantidad(dto.getCantidad());
        plato.setPrecio(dto.getPrecio());
        plato.setStock(dto.getStock());
        plato.setPreparacionCasa(dto.getPreparacionCasa());
        plato.setRecomendaciones(dto.getRecomendaciones());
        plato.setCategoria(categoria);
        plato.setFechaActualizacion(LocalDateTime.now());

        Plato actualizado = platoRepository.save(plato);

        // Actualizar ingredientes: eliminar anteriores y guardar nuevos
        platoIngredienteRepository.deleteByPlato(actualizado);

        if (dto.getIngredientesUsados() != null) {
            for (var usado : dto.getIngredientesUsados()) {
                Ingrediente ing = ingredienteRepository.findById(usado.getIngredienteId())
                        .orElseThrow(() -> new EntityNotFoundException("Ingrediente no encontrado"));

                PlatoIngrediente pi = PlatoIngrediente.builder()
                        .plato(actualizado)
                        .ingrediente(ing)
                        .cantidadUsada(usado.getCantidadUsada())
                        .unidad(usado.getUnidad())
                        .build();

                platoIngredienteRepository.save(pi);
            }
        }

        // Actualizar alérgenos: eliminar anteriores y guardar nuevos
        platoAlergenoRepository.deleteByPlato(actualizado);

        if (dto.getAlergenosIds() != null) {
            for (Integer idAlergeno : dto.getAlergenosIds()) {
                Alergeno alergeno = alergenoRepository.findById(idAlergeno)
                        .orElseThrow(() -> new EntityNotFoundException("Alergeno no encontrado"));

                PlatoAlergeno pa = PlatoAlergeno.builder()
                        .plato(actualizado)
                        .alergeno(alergeno)
                        .build();

                platoAlergenoRepository.save(pa);
            }
        }

        return convertirAResponse(actualizado);
    }

    @Override
    public List<IngredienteUsadoDTO> obtenerIngredientesPorPlato(Integer platoId) {
        Plato plato = platoRepository.findById(platoId)
                .orElseThrow(() -> new EntityNotFoundException("Plato no encontrado"));

        return platoIngredienteRepository.findByPlato(plato).stream()
                .map(pi -> IngredienteUsadoDTO.builder()
                        .nombre(pi.getIngrediente().getNombre())
                        .cantidadUsada(pi.getCantidadUsada())
                        .unidad(pi.getUnidad())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<AlergenoResponseDTO> obtenerAlergenosPorPlato(Integer platoId) {
        Plato plato = platoRepository.findById(platoId)
                .orElseThrow(() -> new EntityNotFoundException("Plato no encontrado"));

        return platoAlergenoRepository.findByPlato(plato).stream()
                .map(pa -> AlergenoResponseDTO.builder()
                        .id(pa.getAlergeno().getId())
                        .nombre(pa.getAlergeno().getNombre())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarPlato(Integer id) {
        if (!platoRepository.existsById(id)) {
            throw new EntityNotFoundException("Plato no encontrado");
        }
        platoRepository.deleteById(id);
    }

    @Override
    public PlatoResponseDTO obtenerPlatoPorId(Integer id) {
        Plato plato = platoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Plato no encontrado"));
        return convertirAResponse(plato);
    }

    @Override
    public List<PlatoListDTO> listarPlatos() {
        return platoRepository.findAll().stream()
                .map(this::convertirAListDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlatoListDTO> buscarPorNombre(String nombre) {
        return platoRepository.findByNombrePlatoContainingIgnoreCase(nombre).stream()
                .map(this::convertirAListDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlatoListDTO> buscarPorCategoria(String categoria) {
        return platoRepository.findByCategoriaNombreIgnoreCase(categoria).stream()
                .map(this::convertirAListDTO)
                .collect(Collectors.toList());
    }

    // Métodos auxiliares

    private PlatoResponseDTO convertirAResponse(Plato plato) {
        List<IngredienteDetalleDTO> ingredientes = plato.getIngredientes().stream()
                .map(pi -> IngredienteDetalleDTO.builder()
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
                .build();
    }


    private PlatoListDTO convertirAListDTO(Plato plato) {
        return PlatoListDTO.builder()
                .id(plato.getId())
                .nombrePlato(plato.getNombrePlato())
                .precio(plato.getPrecio())
                .categoria(plato.getCategoria().getNombre())
                .build();
    }
}
