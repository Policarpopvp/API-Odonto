package com.apiodonto.apiodonto.domain.consultas.validacoes;

import com.apiodonto.apiodonto.domain.consultas.ConsultaRepository;
import com.apiodonto.apiodonto.domain.consultas.DadosAgendamentoConsulta;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteSemOutraConsultaNoDia implements ValidadorAgendamentoConsultas {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        var primeiroHorario =dados.data().withHour(7);
        var ultimoHorario = dados.data().withHour(18);
        var pacientePossuiOutraConsultaNoDia = repository.existsByPacienteIdAndDataBetween(dados.idPaciente(), primeiroHorario, ultimoHorario);
        if(pacientePossuiOutraConsultaNoDia){
            throw new ValidationException("Paciente ja possui uma consulta agendada nesse dia");
        }
    }

}
