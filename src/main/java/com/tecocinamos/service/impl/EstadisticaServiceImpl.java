package com.tecocinamos.service.impl;

import com.tecocinamos.service.EstadisticaServiceI;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class EstadisticaServiceImpl implements EstadisticaServiceI {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Map<String, Object>> obtenerTopPlatos(int topN) {
        // Agrupa por plato sumando cantidad
        String jpql = "SELECT dp.plato.nombrePlato AS nombre, SUM(dp.cantidadPlato) AS total " +
                "FROM DetallesPedido dp " +
                "GROUP BY dp.plato.id " +
                "ORDER BY total DESC";
        Query q = em.createQuery(jpql, Tuple.class);
        @SuppressWarnings("unchecked")
        List<Tuple> resultados = q.getResultList();

        List<Map<String, Object>> lista = new ArrayList<>();
        int contador = 0;
        for (Tuple t : resultados) {
            if (contador++ >= topN) break;
            Map<String, Object> map = new HashMap<>();
            map.put("nombrePlato", t.get("nombre"));
            map.put("cantidadVendida", ((Number) t.get("total")).longValue());
            lista.add(map);
        }
        return lista;
    }

    @Override
    public double obtenerIngresosPorPeriodo(LocalDate fechaDesde, LocalDate fechaHasta) {
        String jpql = """
        SELECT SUM(p.total)
          FROM Pedido p
         WHERE p.fechaCreado BETWEEN :desde AND :hasta
           AND p.estado.nombre <> 'Cancelado'
        """;

        Query q = em.createQuery(jpql);
        q.setParameter("desde", fechaDesde);
        q.setParameter("hasta", fechaHasta);

        // Obtenemos el resultado como Number (o BigDecimal)
        Object raw = q.getSingleResult();
        if (raw == null) {
            return 0.0;
        }

        if (raw instanceof BigDecimal) {
            return ((BigDecimal) raw).doubleValue();
        } else if (raw instanceof Number) {
            return ((Number) raw).doubleValue();
        } else {
            // En principio no debería llegar aquí, pero por seguridad:
            throw new IllegalStateException("Tipo inesperado devuelto por SUM(): " + raw.getClass());
        }
    }


    @Override
    public List<Map<String, Object>> contarPedidosPorEstado() {
        String jpql = "SELECT p.estado.nombre AS estado, COUNT(p) AS cantidad " +
                "FROM Pedido p GROUP BY p.estado.nombre";
        Query q = em.createQuery(jpql, Tuple.class);
        @SuppressWarnings("unchecked")
        List<Tuple> resultados = q.getResultList();

        List<Map<String, Object>> lista = new ArrayList<>();
        for (Tuple t : resultados) {
            Map<String, Object> map = new HashMap<>();
            map.put("estado", t.get("estado"));
            map.put("cantidad", ((Number) t.get("cantidad")).longValue());
            lista.add(map);
        }
        return lista;
    }
}
