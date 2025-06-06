package com.tecocinamos.controller;

import com.tecocinamos.service.FacturaServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/facturas")
public class FacturaController {

    @Autowired
    private FacturaServiceI facturaService;

    /**
     * GET /api/v1/facturas/{pedidoId}
     * Genera y devuelve la factura en PDF para el pedido dado.
     */
    @GetMapping("/{pedidoId}")
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN')")
    public ResponseEntity<ByteArrayResource> descargarFactura(@PathVariable Integer pedidoId) {
        // 1) Llamamos al servicio que genera el PDF en byte[]
        byte[] pdfBytes = facturaService.generarFacturaPdf(pedidoId);

        // 2) Creamos un ByteArrayResource a partir del array de bytes
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        // 3) Armar la cabecera HTTP para un PDF
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=factura_pedido_" + pedidoId + ".pdf");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

        // 4) Devolver el PDF en el body de la respuesta
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(pdfBytes.length)
                .body(resource);
    }
}
