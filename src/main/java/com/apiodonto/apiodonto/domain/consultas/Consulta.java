package com.apiodonto.apiodonto.domain.consultas;

import com.apiodonto.apiodonto.domain.dentista.Dentista;
import com.apiodonto.apiodonto.domain.paciente.Paciente;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "consulta")
@Entity(name = "Consulta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dentista_id")
    private Dentista dentista;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private LocalDateTime data;

    private Boolean cancelada = false;

    private String motivoCancelamento;

    public void cancelar(String motivo) {
        this.cancelada = true;
        this.motivoCancelamento = motivo;
    }
}
