package com.tecocinamos.service.impl;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.tecocinamos.exception.NotFoundException;
import com.tecocinamos.model.DetallesPedido;
import com.tecocinamos.model.Pedido;
import com.tecocinamos.repository.PedidoRepository;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.tecocinamos.service.FacturaServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FacturaServiceImpl implements FacturaServiceI {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public byte[] generarFacturaPdf(Integer pedidoId) {
        // 1) Obtener el pedido de la base de datos
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new NotFoundException("Pedido no encontrado con ID " + pedidoId));

        // 2) Crear un ByteArrayOutputStream para volcar el PDF
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // 3) Inicializar PdfWriter y PdfDocument en memoria
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            // Tamaño de página: A4
            Document document = new Document(pdfDoc, PageSize.A4);
            document.setMargins(20, 20, 20, 20);

            // Fuente estándar
            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

            // 4) Encabezado de factura
            Paragraph titulo = new Paragraph("Factura – Tecocinamos")
                    .setFont(bold)
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(titulo);

            document.add(new Paragraph("\n")); // salto de línea

            // 5) Datos del cliente y del pedido
            // Fecha de creación y entrega
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Cliente: (usamos datos de usuario relacionados)
            String nombreCliente = pedido.getUsuario().getNombre();
            String emailCliente  = pedido.getUsuario().getEmail();
            String telefonoCliente = pedido.getUsuario().getTelefono() != null ? pedido.getUsuario().getTelefono() : "N/A";

            // Construir un pequeño table para datos generales
            Table tablaInfo = new Table(UnitValue.createPercentArray(new float[]{1, 2}))
                    .useAllAvailableWidth();

            tablaInfo.addCell(new Cell().add(new Paragraph("Factura ID:").setFont(bold)));
            tablaInfo.addCell(new Cell().add(new Paragraph(pedido.getId().toString()).setFont(font)));

            tablaInfo.addCell(new Cell().add(new Paragraph("Fecha pedido:").setFont(bold)));
            tablaInfo.addCell(new Cell().add(new Paragraph(pedido.getFechaCreado().format(df)).setFont(font)));

            tablaInfo.addCell(new Cell().add(new Paragraph("Fecha entrega:").setFont(bold)));
            tablaInfo.addCell(new Cell().add(new Paragraph(pedido.getFechaEntrega().format(df)).setFont(font)));

            tablaInfo.addCell(new Cell().add(new Paragraph("Cliente:").setFont(bold)));
            tablaInfo.addCell(new Cell().add(new Paragraph(nombreCliente).setFont(font)));

            tablaInfo.addCell(new Cell().add(new Paragraph("Email:").setFont(bold)));
            tablaInfo.addCell(new Cell().add(new Paragraph(emailCliente).setFont(font)));

            tablaInfo.addCell(new Cell().add(new Paragraph("Teléfono:").setFont(bold)));
            tablaInfo.addCell(new Cell().add(new Paragraph(telefonoCliente).setFont(font)));

            tablaInfo.addCell(new Cell().add(new Paragraph("Dirección de entrega:").setFont(bold)));
            tablaInfo.addCell(new Cell().add(new Paragraph(pedido.getDireccionEntrega()).setFont(font)));

            document.add(tablaInfo);

            document.add(new Paragraph("\n")); // espacio

            // 6) Tabla con los detalles del pedido (cada línea: plato, cantidad, precio unitario, descuento, total línea)
            Paragraph subTitulo = new Paragraph("Detalle de Productos")
                    .setFont(bold)
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(subTitulo);

            // Cabeceras de la tabla
            Table tablaLineas = new Table(UnitValue.createPercentArray(new float[]{4, 1, 2, 2, 2}))
                    .useAllAvailableWidth();
            tablaLineas.addHeaderCell(new Cell().add(new Paragraph("Producto").setFont(bold)));
            tablaLineas.addHeaderCell(new Cell().add(new Paragraph("Cant.").setFont(bold)));
            tablaLineas.addHeaderCell(new Cell().add(new Paragraph("Precio Unit.").setFont(bold)));
            tablaLineas.addHeaderCell(new Cell().add(new Paragraph("Descuento").setFont(bold)));
            tablaLineas.addHeaderCell(new Cell().add(new Paragraph("Total Línea").setFont(bold)));

            // Recorrer cada DetallesPedido
            List<DetallesPedido> lineas = pedido.getDetalles();
            BigDecimal totalPedido = BigDecimal.ZERO;

            if (lineas.isEmpty()) {
                // Si no hay detalles, mostramos una única fila
                tablaLineas.addCell(new Cell(1, 5)
                        .add(new Paragraph("No hay productos en este pedido").setFont(font))
                        .setTextAlignment(TextAlignment.CENTER));
            } else {
                for (DetallesPedido detalle : lineas) {
                    String nombrePlatoLinea = detalle.getPlato().getNombrePlato();
                    Integer cantidadLinea = detalle.getCantidadPlato();
                    BigDecimal precioUnitario = detalle.getPlato().getPrecio();
                    BigDecimal descuentoLinea = detalle.getDescuento() != null
                            ? detalle.getDescuento()
                            : BigDecimal.ZERO;

                    // Calcular subtotal línea = precioUnitario * cantidad - descuento
                    BigDecimal subtotalLinea = precioUnitario
                            .multiply(BigDecimal.valueOf(cantidadLinea))
                            .subtract(descuentoLinea);
                    totalPedido = totalPedido.add(subtotalLinea);

                    tablaLineas.addCell(new Cell().add(new Paragraph(nombrePlatoLinea).setFont(font)));
                    tablaLineas.addCell(new Cell()
                            .add(new Paragraph(cantidadLinea.toString()).setFont(font))
                            .setTextAlignment(TextAlignment.CENTER));
                    tablaLineas.addCell(new Cell()
                            .add(new Paragraph(precioUnitario.setScale(2).toString() + " €").setFont(font))
                            .setTextAlignment(TextAlignment.RIGHT));
                    tablaLineas.addCell(new Cell()
                            .add(new Paragraph(descuentoLinea.setScale(2).toString() + " €").setFont(font))
                            .setTextAlignment(TextAlignment.RIGHT));
                    tablaLineas.addCell(new Cell()
                            .add(new Paragraph(subtotalLinea.setScale(2).toString() + " €").setFont(font))
                            .setTextAlignment(TextAlignment.RIGHT));
                }
            }

            document.add(tablaLineas);

            document.add(new Paragraph("\n"));

            // 7) Total final
            Paragraph totalPar = new Paragraph("Total Factura: " + totalPedido.setScale(2).toString() + " €")
                    .setFont(bold)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.RIGHT);
            document.add(totalPar);

            // 8) Pie de página
            document.add(new Paragraph("\n"));
            String pie = "Gracias por confiar en Tecocinamos. ¡Esperamos verte pronto!";
            document.add(new Paragraph(pie).setFont(font).setFontSize(10).setTextAlignment(TextAlignment.CENTER));

            // Cerrar documento
            document.close();

            // 9) Devolver bytes del PDF
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generando factura PDF: " + e.getMessage(), e);
        }
    }
}
