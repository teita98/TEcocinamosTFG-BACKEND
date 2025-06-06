package com.tecocinamos.controller;

import com.tecocinamos.service.EstadisticaServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
            Date fechaDesde = java.sql.Date.valueOf(desde);
            Date fechaHasta = java.sql.Date.valueOf(hasta);
            double ingresos = estadisticaService.obtenerIngresosPorPeriodo(fechaDesde, fechaHasta);
            Map<String, Object> resp = Map.of(
                    "ingresos", ingresos,
                    "desde", desde,
                    "hasta", hasta
            );
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Formato de fecha inválido. Use YYYY-MM-DD"));
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
