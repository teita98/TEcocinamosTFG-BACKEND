package com.tecocinamos.service.impl;

import com.tecocinamos.dto.LogAuditoriaDTO;
import com.tecocinamos.model.LogAuditoria;
import com.tecocinamos.repository.LogAuditoriaRepository;
import com.tecocinamos.service.LogAuditoriaServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogAuditoriaServiceImpl implements LogAuditoriaServiceI {

    @Autowired
    private LogAuditoriaRepository logAuditoriaRepository;

    @Override
    public List<LogAuditoriaDTO> listarLogs() {
        return logAuditoriaRepository.findAll().stream()
                .map(log -> LogAuditoriaDTO.builder()
                        .id(log.getId())
                        .entidad(log.getEntidad())
                        .campoModificado(log.getCampoModificado())
                        .valorAnterior(log.getValorAnterior())
                        .valorNuevo(log.getValorNuevo())
                        .accion(log.getAccion())
                        .fecha(log.getFecha())
                        .emailAdmin(log.getUsuarioAdmin().getEmail())
                        .build())
                .collect(Collectors.toList());
    }
}
