package com.apiodonto.apiodonto.domain.consultas.validacoes;

import com.apiodonto.apiodonto.domain.consultas.DadosAgendamentoConsulta;
import com.apiodonto.apiodonto.domain.paciente.PacienteRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteAtivo  implements ValidadorAgendamentoConsultas{

    @Autowired
    private PacienteRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        if (dados.idPaciente() == null){
            return;
        }

        var pacienteEstaAtivo = repository.findAtivoById(dados.idDentista());
        if (!pacienteEstaAtivo){
            throw new ValidationException("Consulta não pode ser agendada com paciente excluido");
        }

    }

}
