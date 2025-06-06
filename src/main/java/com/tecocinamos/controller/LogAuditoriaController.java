package com.tecocinamos.controller;

import com.tecocinamos.dto.LogAuditoriaDTO;
import com.tecocinamos.service.LogAuditoriaServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logs")
@PreAuthorize("hasRole('ADMIN')")
public class LogAuditoriaController {

    @Autowired
    private LogAuditoriaServiceI logService;

    /**
     * GET /api/v1/logs
     * Listar todos los logs de auditoría (solo ADMIN).
     */
    @GetMapping
    public ResponseEntity<List<LogAuditoriaDTO>> listar() {
        List<LogAuditoriaDTO> lista = logService.listarLogs();
        return ResponseEntity.ok(lista);
    }
}
