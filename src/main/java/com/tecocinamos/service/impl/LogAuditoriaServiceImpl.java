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
        return logAuditoriaRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    private LogAuditoriaDTO mapToDTO(LogAuditoria log) {
        LogAuditoriaDTO dto = new LogAuditoriaDTO();
        dto.setEntidad(log.getEntidad());
        dto.setCampoModificado(log.getCampoModificado());
        dto.setValorAnterior(log.getValorAnterior());
        dto.setValorNuevo(log.getValorNuevo());
        dto.setAccion(log.getAccion());
        dto.setFecha(log.getFecha());
        dto.setEmailAdmin(log.getUsuarioAdmin().getEmail());
        return dto;
    }
}
