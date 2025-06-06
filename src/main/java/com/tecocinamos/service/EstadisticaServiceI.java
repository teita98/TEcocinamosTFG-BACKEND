package com.tecocinamos.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface EstadisticaServiceI {
    /**
     * Devuelve lista de mapas con {"nombrePlato", String}, {"cantidadVendida", Long}.
     * Ordenada descendente por cantidadVendida, tomando los topN más vendidos.
     */
    List<Map<String, Object>> obtenerTopPlatos(int topN);

    /**
     * Devuelve la suma de totales de todos los pedidos cuyo campo fechaCreado esté entre fechaDesde y fechaHasta,
     * excluyendo aquellos en estado 'Cancelado'.
     */
    double obtenerIngresosPorPeriodo(Date fechaDesde, Date fechaHasta);

    /**
     * Devuelve lista de mapas con {"estado", String}, {"cantidad", Long} para cada estado de pedido.
     */
    List<Map<String, Object>> contarPedidosPorEstado();
}
