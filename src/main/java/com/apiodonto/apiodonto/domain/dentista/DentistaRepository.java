package com.apiodonto.apiodonto.domain.dentista;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface DentistaRepository extends JpaRepository<Dentista, Long> {

  Page<Dentista> findAllByAtivoTrue(Pageable paginacao);

  @Query("""
        select m from Dentista m
        where
            m.ativo = true
            and m.especialidade = :especialidade
            and m.id not in (
                select c.dentista.id from Consulta c
                where c.data = :data
            )
        order by rand()
        limit 1
    """)
  Dentista escolherMedicoAleatorioLivreNaData(Especialidade especialidade, LocalDateTime data);

  @Query("""
        select m.ativo from Dentista m
        where m.id = :id
    """)
  Boolean findAtivoById(Long id);
}
