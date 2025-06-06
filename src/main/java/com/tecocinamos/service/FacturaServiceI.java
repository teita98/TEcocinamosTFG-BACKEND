package com.tecocinamos.service;

public interface FacturaServiceI {
    /**
     * Genera un PDF de factura para el pedido con ID = pedidoId.
     * Devuelve el contenido binario (byte[]) del PDF.
     */
    byte[] generarFacturaPdf(Integer pedidoId);
}
