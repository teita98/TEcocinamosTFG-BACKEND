// src/main/java/com/tecocinamos/service/impl/PedidoServiceImpl.java
package com.tecocinamos.service.impl;

import com.tecocinamos.dto.*;
import com.tecocinamos.exception.BadRequestException;
import com.tecocinamos.exception.NotFoundException;
import com.tecocinamos.model.*;
import com.tecocinamos.repository.*;
import com.tecocinamos.service.PedidoServiceI;
import com.tecocinamos.util.LogAuditoriaUtil;
import com.tecocinamos.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoServiceI {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallesPedidoRepository detallesPedidoRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private PlatoRepository platoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LogAuditoriaUtil logUtil;

    @Override
    public PedidoResponseDTO crearPedido(PedidoRequestDTO dto) {
        String email = SecurityUtil.getAuthenticatedEmail();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Estado estadoInicial = estadoRepository.findByNombreIgnoreCase("Pendiente")
                .orElseThrow(() -> new NotFoundException("Estado 'Pendiente' no encontrado"));

        Pedido pedido = Pedido.builder()
                .usuario(usuario)
                .estado(estadoInicial)
                .fechaCreado(LocalDate.now())
                .fechaEntrega(dto.getFechaEntrega())
                .direccionEntrega(dto.getDireccionEntrega())
                .fechaActualizacion(LocalDateTime.now())
                .total(BigDecimal.ZERO)
                .build();

        Pedido guardado = pedidoRepository.save(pedido);

        BigDecimal total = BigDecimal.ZERO;
        for (DetallesPedidoDTO detalleDTO : dto.getDetalles()) {
            Plato plato = platoRepository.findById(detalleDTO.getPlatoId())
                    .orElseThrow(() -> new NotFoundException("Plato no encontrado con ID " + detalleDTO.getPlatoId()));

            if (plato.getStock() < detalleDTO.getCantidadPlato()) {
                throw new BadRequestException("No hay suficiente stock del plato: " + plato.getNombrePlato());
            }
            // Reducir stock del plato
            plato.setStock(plato.getStock() - detalleDTO.getCantidadPlato());
            platoRepository.save(plato);

            BigDecimal precioUnitario = plato.getPrecio();
            BigDecimal descuento = detalleDTO.getDescuento() != null ? detalleDTO.getDescuento() : BigDecimal.ZERO;
            BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(detalleDTO.getCantidadPlato())).subtract(descuento);

            total = total.add(subtotal);

            DetallesPedido detalles = DetallesPedido.builder()
                    .pedido(guardado)
                    .plato(plato)
                    .cantidadPlato(detalleDTO.getCantidadPlato())
                    .descuento(descuento)
                    .build();

            detallesPedidoRepository.save(detalles);
        }

        guardado.setTotal(total);
        pedidoRepository.save(guardado);

        return mapToResponseDTO(guardado);
    }

    @Override
    public PedidoResponseDTO obtenerPedidoPorId(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido no encontrado con ID " + id));

        String email = SecurityUtil.getAuthenticatedEmail();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        // Si es cliente, solo puede ver su propio pedido
        if (!usuario.getRol().getNombreRol().equalsIgnoreCase("ADMIN")) {
            if (!pedido.getUsuario().getEmail().equals(usuario.getEmail())) {
                throw new BadRequestException("No autorizado para ver este pedido");
            }
        }

        return mapToResponseDTO(pedido);
    }

    @Override
    public List<PedidoListDTO> listarPedidosUsuario() {
        String email = SecurityUtil.getAuthenticatedEmail();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        return pedidoRepository.findByUsuarioId(usuario.getId()).stream()
                .map(this::mapToListDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PedidoListDTO> listarTodosPedidos(Integer estadoId, Pageable pageable) {
        Page<Pedido> pedidos;
        if (estadoId != null) {
            pedidos = pedidoRepository.findByEstadoId(estadoId, pageable);
        } else {
            pedidos = pedidoRepository.findAll(pageable);
        }
        return pedidos.map(this::mapToListDTO);
    }

    @Override
    public PedidoResponseDTO actualizarEstado(Integer pedidoId, Integer nuevoEstadoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new NotFoundException("Pedido no encontrado con ID " + pedidoId));

        Estado nuevoEstado = estadoRepository.findById(nuevoEstadoId)
                .orElseThrow(() -> new NotFoundException("Estado no encontrado con ID " + nuevoEstadoId));

        Estado anterior = pedido.getEstado();
        pedido.setEstado(nuevoEstado);
        pedido.setFechaActualizacion(LocalDateTime.now());
        Pedido actualizado = pedidoRepository.save(pedido);

        String emailAdmin = SecurityUtil.getAuthenticatedEmail();
        logUtil.registrar(
                "Pedido",
                "estado",
                anterior.getNombre(),
                nuevoEstado.getNombre(),
                "Cambio de estado",
                emailAdmin
        );

        return mapToResponseDTO(actualizado);
    }

    @Override
    public void eliminarPedido(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pedido no encontrado con ID " + id));

        String email = SecurityUtil.getAuthenticatedEmail();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        // Si es cliente, solo puede cancelar si es propio y está pendiente
        if (!usuario.getRol().getNombreRol().equalsIgnoreCase("ADMIN")) {
            if (!pedido.getUsuario().getEmail().equals(email)) {
                throw new BadRequestException("No autorizado para eliminar este pedido");
            }
            if (!pedido.getEstado().getNombre().equalsIgnoreCase("Pendiente")) {
                throw new BadRequestException("Solo se puede cancelar un pedido en estado 'Pendiente'");
            }
            pedido.setEstado(estadoRepository.findByNombreIgnoreCase("Cancelado")
                    .orElseThrow(() -> new NotFoundException("Estado 'Cancelado' no encontrado")));
            pedido.setFechaActualizacion(LocalDateTime.now());
            pedidoRepository.save(pedido);
            return;
        }

        // Si es admin, elimina todo (incluyendo restaurar stock de platos)
        // Restaurar stock de platos
        for (DetallesPedido dp : pedido.getDetalles()) {
            Plato plato = dp.getPlato();
            plato.setStock(plato.getStock() + dp.getCantidadPlato());
            platoRepository.save(plato);
        }
        detallesPedidoRepository.deleteAll(pedido.getDetalles());
        pedidoRepository.deleteById(id);
    }

    private PedidoResponseDTO mapToResponseDTO(Pedido pedido) {
        List<DetallesPedidoDTO> detalles = pedido.getDetalles().stream()
                .map(dp -> DetallesPedidoDTO.builder()
                        .platoId(dp.getPlato().getId())
                        .cantidadPlato(dp.getCantidadPlato())
                        .descuento(dp.getDescuento())
                        .build())
                .collect(Collectors.toList());

        return PedidoResponseDTO.builder()
                .id(pedido.getId())
                .estado(pedido.getEstado().getNombre())
                .nombreCliente(pedido.getUsuario().getNombre())
                .emailCliente(pedido.getUsuario().getEmail())
                .fechaCreado(pedido.getFechaCreado())
                .fechaEntrega(pedido.getFechaEntrega())
                .direccionEntrega(pedido.getDireccionEntrega())
                .total(pedido.getTotal())
                .fechaActualizacion(pedido.getFechaActualizacion())
                .detalles(detalles)
                .build();
    }

    private PedidoListDTO mapToListDTO(Pedido pedido) {
        int totalPlatos = pedido.getDetalles().stream()
                .mapToInt(DetallesPedido::getCantidadPlato)
                .sum();
        return PedidoListDTO.builder()
                .id(pedido.getId())
                .nombreCliente(pedido.getUsuario().getNombre())
                .estado(pedido.getEstado().getNombre())
                .fechaEntrega(pedido.getFechaEntrega())
                .totalPlatos(totalPlatos)
                .total(pedido.getTotal())
                .build();
    }
}
