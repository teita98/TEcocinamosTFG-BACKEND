package com.tecocinamos.controller;

import com.tecocinamos.dto.ContactFormDTO;
import com.tecocinamos.dto.EventInquiryDTO;
import com.tecocinamos.service.mail.MailServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contact")
public class ContactController {

    private final MailServiceI mailService;

    @Autowired
    public ContactController(MailServiceI mailService) {
        this.mailService = mailService;
    }

    /**
     * 1) Formulario de “evento”
     *    POST /api/v1/contact/evento
     */
    @PostMapping("/evento")
    public ResponseEntity<String> submitEventInquiry(@Valid @RequestBody EventInquiryDTO dto) {
        // 1.1) Enviar email de confirmación al usuario
        String to = dto.getEmail();
        String subject = "Hemos recibido tu consulta de evento";
        String body = """
                Hola %s,

                Gracias por escribirnos acerca de tu evento:
                “%s”

                Hemos recibido tu mensaje y uno de nuestros asesores de catering te contactará a la brevedad al teléfono %s o por este mismo correo.

                Un saludo,
                El equipo de Tecocinamos
                """.formatted(dto.getNombre(), dto.getMensaje(), dto.getTelefono());

        mailService.sendPlainTextEmail(to, subject, body);

        // 1.2) Avisar internamente al admin
        String adminEmail = "tecocinamoscatering@gmail.com";
        String subjectAdmin = "Nueva consulta de evento desde el front: " + dto.getNombre();
        String bodyAdmin = """
                El usuario %s (%s / %s) ha enviado una consulta de evento:

                %s
                """.formatted(
                dto.getNombre(), dto.getEmail(), dto.getTelefono(),
                dto.getMensaje()
        );
        mailService.sendPlainTextEmail(adminEmail, subjectAdmin, bodyAdmin);

        return ResponseEntity.ok("Formulario de evento recibido y correo enviado.");
    }

    /**
     * 2) Formulario genérico de “Contáctanos”
     *    POST /api/v1/contact/general
     */
    @PostMapping("/general")
    public ResponseEntity<String> submitContactForm(@Valid @RequestBody ContactFormDTO dto) {
        // 2.1) Enviar email de confirmación al usuario
        String to = dto.getEmail();
        String subject = "Hemos recibido tu mensaje de contacto";
        String body = """
                Hola %s,

                Gracias por comunicarte con nosotros. Hemos recibido tu mensaje:
                "%s"

                Uno de nuestros asesores te contactará pronto al teléfono %s o por este mismo correo.

                Un saludo,
                El equipo de Tecocinamos
                """.formatted(dto.getNombre(), dto.getMensaje(), dto.getTelefono());

        mailService.sendPlainTextEmail(to, subject, body);

        // 2.2) Notificar por email a un admin
        String adminEmail = "tecocinamoscatering@gmail.com";
        String subjectAdmin = "Nuevo formulario de contacto de: " + dto.getNombre();
        String bodyAdmin = """
                El usuario %s (%s / %s) ha enviado un mensaje de contacto:

                Mensaje:
                %s
                """.formatted(
                dto.getNombre(), dto.getEmail(), dto.getTelefono(),
                dto.getMensaje()
        );
        mailService.sendPlainTextEmail(adminEmail, subjectAdmin, bodyAdmin);

        return ResponseEntity.ok("Formulario de contacto recibido y correo enviado.");
    }
}
