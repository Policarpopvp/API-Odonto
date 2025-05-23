package com.apiodonto.apiodonto.domain.dentista;

import com.apiodonto.apiodonto.domain.endereco.Endereco;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;


@Table(name = "dentista")
@Entity(name = "Dentista")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Dentista {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
     private String nome;
     private String email;
     private String cro;
     private String telefone;

     @Enumerated(EnumType.STRING)
     private Especialidade especialidade;

     @Embedded
     private Endereco endereco;

     private boolean ativo;

    public Dentista(DadosCadastroDentista dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.cro = dados.cro();
        this.email = dados.email();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
        this.telefone = dados.telefone();
    }

    public void atualizarInformacoes(@Valid DadosAtualizacaoDentista dados) {
       if (dados.nome() != null){       //esse if,assim como todos, serve para que na hora do put se voce quiser mudar apenas um campo ele nao troque os outros para null
           this.nome = dados.nome();    //ou seja ele so quer atualizar se o campo vier no metodo put se nao vai continuar como esta
       }
        if (dados.telefone() != null){
            this.telefone = dados.telefone();
        }
        if (dados.endereco() != null){
            this.endereco.atualizarInformacoes(dados.endereco());
        }
    }

    public void excluir() {
        this.ativo = false; //desativa os dentistas ativos
    }
}
