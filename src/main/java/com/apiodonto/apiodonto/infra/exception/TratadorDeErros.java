package com.apiodonto.apiodonto.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidation::new).toList());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity tratarErroValidacao(ValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    public record DadosErroValidation(String campo, String mensagem) {
        public DadosErroValidation(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
