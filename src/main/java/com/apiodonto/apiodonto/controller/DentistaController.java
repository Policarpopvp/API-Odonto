package com.apiodonto.apiodonto.controller;

import com.apiodonto.apiodonto.domain.dentista.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/dentista")
@SecurityRequirement(name = "bearer-key")
public class DentistaController {

    @Autowired
    private DentistaRepository repository;



    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity detalhar(@PathVariable Long id){ //PathVariable para dizer ao spring que o id é o mesmo da url
        var dentista = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoDentista(dentista));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemDentista>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemDentista :: new);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroDentista dados, UriComponentsBuilder uriBuilder){

       var dentista = new Dentista(dados);
        repository.save(dentista);

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(dentista.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoDentista(dentista));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoDentista dados){
        var dentista = repository.getReferenceById(dados.id());
        dentista.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoDentista(dentista));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletar(@PathVariable Long id){ //PathVariable para dizer ao spring que o id é o mesmo da url
        var dentista = repository.getReferenceById(id);
        dentista.excluir();

        return ResponseEntity.noContent().build();
    }
}