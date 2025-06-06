package com.tecocinamos.controller;

import com.tecocinamos.service.EstadisticaServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/estadisticas")
@PreAuthorize("hasRole('ADMIN')")
public class EstadisticaController {

    @Autowired
    private EstadisticaServiceI estadisticaService;

    /**
     * GET /api/v1/estadisticas/top-platos?topN=5
     * Retorna lista de { nombrePlato, cantidadVendida } ordenados desc.
     */
    @GetMapping("/top-platos")
    public ResponseEntity<List<Map<String, Object>>> obtenerTopPlatos(
            @RequestParam(defaultValue = "5") int topN) {
        List<Map<String, Object>> lista = estadisticaService.obtenerTopPlatos(topN);
        return ResponseEntity.ok(lista);
    }

    /**
     * GET /api/v1/estadisticas/ingresos?desde=2025-01-01&hasta=2025-06-01
     */
    @GetMapping("/ingresos")
    public ResponseEntity<Map<String, Object>> ingresosPorPeriodo(
            @RequestParam String desde,
            @RequestParam String hasta) {
        try {
            LocalDate ldDesde = LocalDate.parse(desde, DateTimeFormatter.ISO_DATE);
            LocalDate ldHasta = LocalDate.parse(hasta, DateTimeFormatter.ISO_DATE);

            double ingresos = estadisticaService.obtenerIngresosPorPeriodo(ldDesde, ldHasta);

            Map<String, Object> resp = Map.of(
                    "ingresos", ingresos,
                    "desde",    desde,
                    "hasta",    hasta
            );
            return ResponseEntity.ok(resp);

        } catch (DateTimeParseException ex) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", "Formato de fecha inválido. Use YYYY-MM-DD"));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno: " + ex.getMessage()));
        }
    }

    /**
     * GET /api/v1/estadisticas/pedidos-por-estado
     */
    @GetMapping("/pedidos-por-estado")
    public ResponseEntity<List<Map<String, Object>>> contarPedidosPorEstado() {
        List<Map<String, Object>> lista = estadisticaService.contarPedidosPorEstado();
        return ResponseEntity.ok(lista);
    }
}
