package com.apiodonto.apiodonto.domain.consultas.validacoes;

import com.apiodonto.apiodonto.domain.consultas.ConsultaRepository;
import com.apiodonto.apiodonto.domain.consultas.DadosAgendamentoConsulta;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoComOutraConsulta implements ValidadorAgendamentoConsultas {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        var dentistaPossuiOutraConsultaNoMesmoHorario = repository.existsByDentistaIdAndData(dados.idDentista(),dados.data());
        if (dentistaPossuiOutraConsultaNoMesmoHorario){
            throw new ValidationException("Dentista ja possui consulta nesse mesmo horário");
        }
    }
}
