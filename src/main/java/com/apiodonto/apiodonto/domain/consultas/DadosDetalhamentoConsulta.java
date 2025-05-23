package com.apiodonto.apiodonto.domain.consultas;

import java.time.LocalDateTime;

public record DadosDetalhamentoConsulta(Long id, Long idMedico, Long idPaciente, LocalDateTime data) {
    public DadosDetalhamentoConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getDentista().getId(),consulta.getPaciente().getId(),consulta.getData());
    }
}
