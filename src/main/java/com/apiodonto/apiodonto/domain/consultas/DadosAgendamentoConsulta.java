package com.apiodonto.apiodonto.domain.consultas;

import com.apiodonto.apiodonto.domain.dentista.Especialidade;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(
        
        Long id,

        Long idDentista,

        @NotNull
        Long idPaciente,

        @NotNull
        @Future
        LocalDateTime data,

        Especialidade especialidade) {
}
