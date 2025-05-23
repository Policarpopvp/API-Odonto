package com.apiodonto.apiodonto.domain.consultas;

import com.apiodonto.apiodonto.domain.consultas.validacoes.ValidadorAgendamentoConsultas;
import com.apiodonto.apiodonto.domain.dentista.Dentista;
import com.apiodonto.apiodonto.domain.dentista.DentistaRepository;
import com.apiodonto.apiodonto.domain.paciente.PacienteRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private DentistaRepository dentistaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private List<ValidadorAgendamentoConsultas> validadores;

    public DadosDetalhamentoConsulta agendar(DadosAgendamentoConsulta dados) {
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidationException("Id do paciente informado não existe");
        }
        if (dados.idDentista() != null && !dentistaRepository.existsById(dados.idDentista())) {
            throw new ValidationException("Id do dentista informado não existe");
        }

        validadores.forEach(v -> v.validar(dados));

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        var dentista = escolherDentista(dados);

        if (dentista == null) {
            throw new ValidationException("Não existe médico disponível nesta data");
        }

        var consulta = new Consulta(null, dentista, paciente, dados.data(), false, null);
        consultaRepository.save(consulta);

        return new DadosDetalhamentoConsulta(consulta);
    }

    public void cancelar(DadosCancelamentoConsulta dados) {
        var consulta = consultaRepository.findById(dados.idConsulta())
                .orElseThrow(() -> new ValidationException("Consulta não encontrada"));

        if (Boolean.TRUE.equals(consulta.getCancelada())) {
            throw new ValidationException("Consulta já está cancelada");
        }

        consulta.cancelar(dados.motivo());
    }

    private Dentista escolherDentista(DadosAgendamentoConsulta dados) {
        if (dados.idDentista() != null) {
            return dentistaRepository.getReferenceById(dados.idDentista());
        }

        if (dados.especialidade() == null) {
            throw new ValidationException("Especialidade obrigatória quando dentista não for escolhido");
        }

        return dentistaRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }
}
