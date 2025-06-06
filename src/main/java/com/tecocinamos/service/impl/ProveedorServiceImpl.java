package com.tecocinamos.service.impl;

import com.tecocinamos.dto.ProveedorRequestDTO;
import com.tecocinamos.dto.ProveedorResponseDTO;
import com.tecocinamos.exception.BadRequestException;
import com.tecocinamos.exception.NotFoundException;
import com.tecocinamos.model.Proveedor;
import com.tecocinamos.repository.ProveedorRepository;
import com.tecocinamos.service.ProveedorServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProveedorServiceImpl implements ProveedorServiceI {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public ProveedorResponseDTO crearProveedor(ProveedorRequestDTO dto) {
        if (proveedorRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new BadRequestException("Ya existe un proveedor con ese nombre");
        }
        Proveedor proveedor = Proveedor.builder()
                .nombre(dto.getNombre())
                .contacto(dto.getContacto())
                .telefono(dto.getTelefono())
                .email(dto.getEmail())
                .build();
        Proveedor guardado = proveedorRepository.save(proveedor);
        return ProveedorResponseDTO.builder()
                .id(guardado.getId())
                .nombre(guardado.getNombre())
                .contacto(guardado.getContacto())
                .telefono(guardado.getTelefono())
                .email(guardado.getEmail())
                .build();
    }

    @Override
    public List<ProveedorResponseDTO> obtenerTodos() {
        return proveedorRepository.findAll().stream()
                .map(p -> ProveedorResponseDTO.builder()
                        .id(p.getId())
                        .nombre(p.getNombre())
                        .contacto(p.getContacto())
                        .telefono(p.getTelefono())
                        .email(p.getEmail())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ProveedorResponseDTO obtenerPorId(Integer id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Proveedor no encontrado con ID " + id));
        return ProveedorResponseDTO.builder()
                .id(proveedor.getId())
                .nombre(proveedor.getNombre())
                .contacto(proveedor.getContacto())
                .telefono(proveedor.getTelefono())
                .email(proveedor.getEmail())
                .build();
    }

    @Override
    public ProveedorResponseDTO actualizarProveedor(Integer id, ProveedorRequestDTO dto) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Proveedor no encontrado con ID " + id));

        if (!proveedor.getNombre().equalsIgnoreCase(dto.getNombre())
                && proveedorRepository.existsByNombreIgnoreCase(dto.getNombre())) {
            throw new BadRequestException("Ya existe un proveedor con ese nombre");
        }

        proveedor.setNombre(dto.getNombre());
        proveedor.setContacto(dto.getContacto());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setEmail(dto.getEmail());

        Proveedor actualizado = proveedorRepository.save(proveedor);
        return ProveedorResponseDTO.builder()
                .id(actualizado.getId())
                .nombre(actualizado.getNombre())
                .contacto(actualizado.getContacto())
                .telefono(actualizado.getTelefono())
                .email(actualizado.getEmail())
                .build();
    }

    @Override
    public void eliminarProveedor(Integer id) {
        if (!proveedorRepository.existsById(id)) {
            throw new NotFoundException("Proveedor no encontrado con ID " + id);
        }
        proveedorRepository.deleteById(id);
    }
}
