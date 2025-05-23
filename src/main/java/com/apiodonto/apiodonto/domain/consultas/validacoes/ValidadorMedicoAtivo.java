package com.apiodonto.apiodonto.domain.consultas.validacoes;

import com.apiodonto.apiodonto.domain.consultas.DadosAgendamentoConsulta;
import com.apiodonto.apiodonto.domain.dentista.DentistaRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoAtivo implements ValidadorAgendamentoConsultas {

    @Autowired
    private DentistaRepository repository;

    public void validar(DadosAgendamentoConsulta dados){
        if (dados.idDentista() == null){
            return;
        }

        var dentistaEstaAtivo = repository.findAtivoById(dados.idDentista());
        if (!dentistaEstaAtivo){
            throw new ValidationException("Consulta não pode ser agendada com dentista excluido");
        }

    }

}
