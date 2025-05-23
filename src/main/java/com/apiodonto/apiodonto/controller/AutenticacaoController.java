package com.apiodonto.apiodonto.controller;

import com.apiodonto.apiodonto.domain.usuario.DadosAutenticacao;
import com.apiodonto.apiodonto.domain.usuario.Usuario;
import com.apiodonto.apiodonto.infra.security.DadosTokenJWT;
import com.apiodonto.apiodonto.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados){ //crio um dto paa autenticacao
       var token = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha()); // crio uma variavel para transformar o dto criado por um que o spring possui e reconhece
        var autentication =  manager.authenticate(token); //disparo a autenticacao

        var tokenJWT = tokenService.gerarToken((Usuario) autentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
