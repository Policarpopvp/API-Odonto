package com.apiodonto.apiodonto.domain.dentista;

import com.apiodonto.apiodonto.domain.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoDentista(

        @NotNull
        Long id,

        String nome,

        String telefone,

        DadosEndereco endereco) {
}
