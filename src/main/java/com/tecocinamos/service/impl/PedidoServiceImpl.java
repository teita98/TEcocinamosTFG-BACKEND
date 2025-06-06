package com.tecocinamos.service.impl;

import com.tecocinamos.dto.*;
import com.tecocinamos.model.*;
import com.tecocinamos.repository.*;
import com.tecocinamos.security.JwtUtils;
import com.tecocinamos.service.PedidoServiceI;
import com.tecocinamos.util.LogAuditoriaUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Estado estadoInicial = estadoRepository.findByNombreIgnoreCase("Pendiente")
                .orElseThrow(() -> new EntityNotFoundException("Estado 'Pendiente' no encontrado"));

        Pedido pedido = Pedido.builder()
                .usuario(usuario)
                .estado(estadoInicial)
                .fechaCreado(LocalDate.now())
                .fechaEntrega(dto.getFechaEntrega())
                .direccionEntrega(dto.getDireccionEntrega())
                .fechaActualizacion(LocalDateTime.now())
                .build();

        Pedido guardado = pedidoRepository.save(pedido);

        BigDecimal total = BigDecimal.ZERO;

        for (DetallesPedidoDTO detalleDTO : dto.getDetalles()) {
            Plato plato = platoRepository.findById(detalleDTO.getPlatoId())
                    .orElseThrow(() -> new EntityNotFoundException("Plato no encontrado: ID " + detalleDTO.getPlatoId()));

            BigDecimal precioUnitario = plato.getPrecio();
            BigDecimal descuento = detalleDTO.getDescuento() != null ? detalleDTO.getDescuento() : BigDecimal.ZERO;
            BigDecimal subtotal = precioUnitario.multiply(BigDecimal.valueOf(detalleDTO.getCantidadPlato()))
                    .subtract(descuento);

            total = total.add(subtotal);

            DetallesPedido detalles = DetallesPedido.builder()
                    .pedido(guardado)
                    .plato(plato)
                    .cantidadPlato(detalleDTO.getCantidadPlato())
                    .descuento(descuento)
                    .build();

            detallesPedidoRepository.save(detalles);
        }

        return mapToResponseDTO(guardado);
    }

    @Override
    public PedidoResponseDTO obtenerPedidoPorId(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));

        return mapToResponseDTO(pedido);
    }

    @Override
    public List<PedidoListDTO> listarPedidos() {
        return pedidoRepository.findAll().stream()
                .map(p -> PedidoListDTO.builder()
                        .id(p.getId())
                        .estado(p.getEstado().getNombre())
                        .fechaEntrega(p.getFechaEntrega())
                        .nombreCliente(p.getUsuario().getNombre())
                        .build())
                .collect(Collectors.toList());
    }


    @Override
    public List<PedidoResponseDTO> listarPedidosUsuario() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        return pedidoRepository.findByUsuarioId(usuario.getId())
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PedidoResponseDTO actualizarEstado(Integer pedidoId, Integer nuevoEstadoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado"));

        Estado nuevoEstado = estadoRepository.findById(nuevoEstadoId)
                .orElseThrow(() -> new EntityNotFoundException("Estado no encontrado"));

        Estado anterior = pedido.getEstado();
        pedido.setEstado(nuevoEstado);
        pedido.setFechaActualizacion(LocalDateTime.now());

        Pedido actualizado = pedidoRepository.save(pedido);

        logUtil.registrar("Pedido", "estado", anterior.getNombre(), nuevoEstado.getNombre(), "Cambio de estado", pedido.getUsuario().getEmail());

        return mapToResponseDTO(actualizado);
    }

    @Override
    public void eliminarPedido(Integer id) {
        if (!pedidoRepository.existsById(id)) {
            throw new EntityNotFoundException("Pedido no encontrado");
        }
        pedidoRepository.deleteById(id);
    }


    private PedidoResponseDTO mapToResponseDTO(Pedido pedido) {
        List<DetallesPedidoDTO> detalles = pedido.getDetalles().stream()
                .map(dp -> DetallesPedidoDTO.builder()
                        .platoId(dp.getPlato().getId())
                        .nombrePlato(dp.getPlato().getNombrePlato())
                        .cantidadPlato(dp.getCantidadPlato())
                        .descuento(dp.getDescuento())
                        .build())
                .collect(Collectors.toList());

        return PedidoResponseDTO.builder()
                .id(pedido.getId())
                .nombreCliente(pedido.getUsuario().getNombre())
                .emailCliente(pedido.getUsuario().getEmail())
                .estado(pedido.getEstado().getNombre())
                .fechaCreado(pedido.getFechaCreado())
                .fechaEntrega(pedido.getFechaEntrega())
                .direccionEntrega(pedido.getDireccionEntrega())
                .detalles(detalles)
                .build();
    }
}
