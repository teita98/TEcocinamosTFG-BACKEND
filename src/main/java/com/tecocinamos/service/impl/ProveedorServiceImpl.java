package com.tecocinamos.service.impl;

import com.tecocinamos.dto.ProveedorRequestDTO;
import com.tecocinamos.dto.ProveedorResponseDTO;
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
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(dto.getNombre());
        proveedor.setContacto(dto.getContacto());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setEmail(dto.getEmail());

        Proveedor guardado = proveedorRepository.save(proveedor);
        return convertirADTO(guardado);
    }

    @Override
    public List<ProveedorResponseDTO> obtenerTodos() {
        return proveedorRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProveedorResponseDTO obtenerPorId(Integer id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        return convertirADTO(proveedor);
    }

    @Override
    public ProveedorResponseDTO actualizarProveedor(Integer id, ProveedorRequestDTO dto) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        proveedor.setNombre(dto.getNombre());
        proveedor.setContacto(dto.getContacto());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setEmail(dto.getEmail());

        Proveedor actualizado = proveedorRepository.save(proveedor);
        return convertirADTO(actualizado);
    }

    @Override
    public void eliminarProveedor(Integer id) {
        if (!proveedorRepository.existsById(id)) {
            throw new RuntimeException("Proveedor no encontrado");
        }
        proveedorRepository.deleteById(id);
    }

    private ProveedorResponseDTO convertirADTO(Proveedor proveedor) {
        ProveedorResponseDTO dto = new ProveedorResponseDTO();
        dto.setId(proveedor.getId());
        dto.setNombre(proveedor.getNombre());
        dto.setContacto(proveedor.getContacto());
        dto.setTelefono(proveedor.getTelefono());
        dto.setEmail(proveedor.getEmail());
        return dto;
    }
}
