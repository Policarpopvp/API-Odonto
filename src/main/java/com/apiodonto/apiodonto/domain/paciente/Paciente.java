package com.apiodonto.apiodonto.domain.paciente;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

@Table(name = "paciente")
@Entity(name = "Paciente")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private boolean ativo;

    public Paciente(DadosCadastroPaciente dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.email = dados.email();
        this.cpf = dados.cpf();
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoPaciente dados) {
        if (dados.nome() != null) {  // Atualiza o nome apenas se o campo nome for enviado na requisição
            this.nome = dados.nome();
        }
    }

    public void excluir() {
        this.ativo = false;  // Desativa o paciente
    }
}
