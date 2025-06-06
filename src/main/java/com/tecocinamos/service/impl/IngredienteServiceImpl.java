package com.tecocinamos.service.impl;

import com.tecocinamos.dto.IngredienteRequestDTO;
import com.tecocinamos.dto.IngredienteResponseDTO;
import com.tecocinamos.model.Ingrediente;
import com.tecocinamos.model.Proveedor;
import com.tecocinamos.repository.IngredienteRepository;
import com.tecocinamos.repository.ProveedorRepository;
import com.tecocinamos.service.IngredienteServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredienteServiceImpl implements IngredienteServiceI {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public IngredienteResponseDTO crearIngrediente(IngredienteRequestDTO dto) {
        Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        Ingrediente ingrediente = Ingrediente.builder()
                .nombre(dto.getNombre())
                .categoria(dto.getCategoria())
                .proveedor(proveedor)
                .cantidadEnvase(dto.getCantidadEnvase())
                .unidadEnvase(dto.getUnidadEnvase())
                .precioEnvase(dto.getPrecioEnvase())
                .precioUnitario(dto.getPrecioEnvase().divide(dto.getCantidadEnvase(), 4, BigDecimal.ROUND_HALF_UP))
                .unidad(dto.getUnidad())
                .build();

        Ingrediente guardado = ingredienteRepository.save(ingrediente);

        return mapToDTO(guardado);
    }

    @Override
    public List<IngredienteResponseDTO> listarIngredientes() {
        return ingredienteRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public IngredienteResponseDTO actualizarIngrediente(Integer id, IngredienteRequestDTO dto) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));

        Proveedor proveedor = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        ingrediente.setNombre(dto.getNombre());
        ingrediente.setCategoria(dto.getCategoria());
        ingrediente.setProveedor(proveedor);
        ingrediente.setCantidadEnvase(dto.getCantidadEnvase());
        ingrediente.setUnidadEnvase(dto.getUnidadEnvase());
        ingrediente.setPrecioEnvase(dto.getPrecioEnvase());
        ingrediente.setUnidad(dto.getUnidad());
        ingrediente.setPrecioUnitario(dto.getPrecioEnvase().divide(dto.getCantidadEnvase(), 4, BigDecimal.ROUND_HALF_UP));

        ingredienteRepository.save(ingrediente);
        return mapToDTO(ingrediente);
    }

    @Override
    public IngredienteResponseDTO obtenerIngredientePorId(Integer id) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));
        return mapToDTO(ingrediente);
    }

    @Override
    public void eliminarIngrediente(Integer id) {
        if (!ingredienteRepository.existsById(id)) {
            throw new RuntimeException("Ingrediente no encontrado");
        }
        ingredienteRepository.deleteById(id);
    }

    private IngredienteResponseDTO mapToDTO(Ingrediente ingrediente) {
        return IngredienteResponseDTO.builder()
                .id(ingrediente.getId())
                .nombre(ingrediente.getNombre())
                .categoria(ingrediente.getCategoria())
                .cantidadEnvase(ingrediente.getCantidadEnvase())
                .unidadEnvase(ingrediente.getUnidadEnvase())
                .precioEnvase(ingrediente.getPrecioEnvase())
                .precioUnitario(ingrediente.getPrecioUnitario())
                .unidad(ingrediente.getUnidad())
                .proveedor(ingrediente.getProveedor().getNombre())
                .build();
    }
}
