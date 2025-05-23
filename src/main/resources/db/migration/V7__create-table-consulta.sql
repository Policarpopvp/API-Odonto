CREATE table consulta(
    id BIGSERIAL PRIMARY KEY,
    dentista_id BIGINT NOT NULL,
    paciente_id BIGINT NOT NULL,
    data timestamp NOT NULL,

    CONSTRAINT fk_consultas_dentista_id FOREIGN KEY (dentista_id) REFERENCES dentista(id),
    CONSTRAINT fk_consultas_paciente_id FOREIGN KEY (paciente_id) REFERENCES paciente(id)
);