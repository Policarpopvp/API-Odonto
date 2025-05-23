package com.apiodonto.apiodonto.domain.consultas;

import java.time.LocalDateTime;

public record DadosListagemConsulta(
        Long id,
        String nomePaciente,
        String nomeDentista,
        LocalDateTime data,
        Boolean cancelada
) {
    public DadosListagemConsulta(Consulta consulta) {
        this(
                consulta.getId(),
                consulta.getPaciente().getNome(),
                consulta.getDentista().getNome(),
                consulta.getData(),
                consulta.getCancelada()
        );
    }
}
