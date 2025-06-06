package com.tecocinamos.service.impl;

import com.tecocinamos.dto.AlergenoResponseDTO;
import com.tecocinamos.dto.IngredienteRequestDTO;
import com.tecocinamos.dto.IngredienteResponseDTO;
import com.tecocinamos.exception.BadRequestException;
import com.tecocinamos.exception.NotFoundException;
import com.tecocinamos.model.Alergeno;
import com.tecocinamos.model.Ingrediente;
import com.tecocinamos.model.IngredienteAlergeno;
import com.tecocinamos.model.Proveedor;
import com.tecocinamos.repository.AlergenoRepository;
import com.tecocinamos.repository.IngredienteAlergenoRepository;
import com.tecocinamos.repository.IngredienteRepository;
import com.tecocinamos.repository.ProveedorRepository;
import com.tecocinamos.service.IngredienteServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredienteServiceImpl implements IngredienteServiceI {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private AlergenoRepository alergenoRepository;

    @Autowired
    private IngredienteAlergenoRepository ingredienteAlergenoRepository;

    @Override
    public IngredienteResponseDTO crearIngrediente(IngredienteRequestDTO dto) {
        Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> new NotFoundException("Proveedor no encontrado con ID " + dto.getProveedorId()));

        Ingrediente ingrediente = Ingrediente.builder()
                .nombre(dto.getNombre())
                .categoria(dto.getCategoria())
                .proveedor(proveedor)
                .cantidadEnvase(dto.getCantidadEnvase())
                .unidadEnvase(dto.getUnidadEnvase())
                .precioEnvase(dto.getPrecioEnvase())
                .precioUnitario(dto.getPrecioEnvase().divide(dto.getCantidadEnvase(), 4, RoundingMode.HALF_UP))
                .unidad(dto.getUnidad())
                .build();

        Ingrediente guardado = ingredienteRepository.save(ingrediente);

        // Asociar alérgenos si hay ids
        if (dto.getAlergenosIds() != null) {
            for (Integer idAler : dto.getAlergenosIds()) {
                Alergeno al = alergenoRepository.findById(idAler)
                        .orElseThrow(() -> new NotFoundException("Alérgeno no encontrado con ID " + idAler));
                IngredienteAlergeno ia = IngredienteAlergeno.builder()
                        .ingrediente(guardado)
                        .alergeno(al)
                        .build();
                ingredienteAlergenoRepository.save(ia);
            }
        }
        return mapToDTO(guardado);
    }

    @Override
    public Page<IngredienteResponseDTO> listarIngredientes(Pageable pageable) {
        return ingredienteRepository.findAll(pageable)
                .map(this::mapToDTO);
    }

    @Override
    public IngredienteResponseDTO obtenerIngredientePorId(Integer id) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ingrediente no encontrado con ID " + id));
        return mapToDTO(ingrediente);
    }

    @Override
    public Page<IngredienteResponseDTO> listarPorProveedor(Integer proveedorId, Pageable pageable) {
        return ingredienteRepository.findByProveedorId(proveedorId, pageable)
                .map(this::mapToDTO);
    }

    @Override
    public IngredienteResponseDTO actualizarIngrediente(Integer id, IngredienteRequestDTO dto) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Ingrediente no encontrado con ID " + id));

        Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> new NotFoundException("Proveedor no encontrado con ID " + dto.getProveedorId()));

        ingrediente.setNombre(dto.getNombre());
        ingrediente.setCategoria(dto.getCategoria());
        ingrediente.setProveedor(proveedor);
        ingrediente.setCantidadEnvase(dto.getCantidadEnvase());
        ingrediente.setUnidadEnvase(dto.getUnidadEnvase());
        ingrediente.setPrecioEnvase(dto.getPrecioEnvase());
        ingrediente.setUnidad(dto.getUnidad());
        ingrediente.setPrecioUnitario(dto.getPrecioEnvase().divide(dto.getCantidadEnvase(), 4, RoundingMode.HALF_UP));

        // Actualizar alérgenos: eliminar anteriores y agregar nuevos
        List<IngredienteAlergeno> antiguos = ingredienteAlergenoRepository.findByIngredienteId(id);
        ingredienteAlergenoRepository.deleteAll(antiguos);

        if (dto.getAlergenosIds() != null) {
            for (Integer idAler : dto.getAlergenosIds()) {
                Alergeno al = alergenoRepository.findById(idAler)
                        .orElseThrow(() -> new NotFoundException("Alérgeno no encontrado con ID " + idAler));
                IngredienteAlergeno ia = IngredienteAlergeno.builder()
                        .ingrediente(ingrediente)
                        .alergeno(al)
                        .build();
                ingredienteAlergenoRepository.save(ia);
            }
        }

        Ingrediente guardado = ingredienteRepository.save(ingrediente);
        return mapToDTO(guardado);
    }

    @Override
    public void eliminarIngrediente(Integer id) {
        if (!ingredienteRepository.existsById(id)) {
            throw new NotFoundException("Ingrediente no encontrado con ID " + id);
        }
        ingredienteRepository.deleteById(id);
    }

    private IngredienteResponseDTO mapToDTO(Ingrediente ingrediente) {
        List<AlergenoResponseDTO> alergenos = ingredienteAlergenoRepository
                .findByIngredienteId(ingrediente.getId())
                .stream()
                .map(ia -> AlergenoResponseDTO.builder()
                        .id(ia.getAlergeno().getId())
                        .nombre(ia.getAlergeno().getNombre())
                        .build())
                .distinct()
                .collect(Collectors.toList());

        return IngredienteResponseDTO.builder()
                .id(ingrediente.getId())
                .nombre(ingrediente.getNombre())
                .categoria(ingrediente.getCategoria())
                .proveedor(ingrediente.getProveedor().getNombre())
                .cantidadEnvase(ingrediente.getCantidadEnvase())
                .unidadEnvase(ingrediente.getUnidadEnvase())
                .precioEnvase(ingrediente.getPrecioEnvase())
                .precioUnitario(ingrediente.getPrecioUnitario())
                .unidad(ingrediente.getUnidad())
                .alergenos(alergenos)
                .build();
    }
}
