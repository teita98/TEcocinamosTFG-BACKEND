package com.tecocinamos.controller;

import com.tecocinamos.dto.LogAuditoriaDTO;
import com.tecocinamos.service.LogAuditoriaServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logs")
public class LogAuditoriaController {

    @Autowired
    private LogAuditoriaServiceI logService;

    // GET /api/v1/logs → ver todos los logs (solo admin idealmente)
    @GetMapping
    public List<LogAuditoriaDTO> listar() {
        return logService.listarLogs();
    }
}
