package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.exception.AbrigoNaoEncontradoException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(AbrigoNaoEncontradoException.class)
    public ResponseEntity<Void> hanlderAbrigoNaoEncontrado() {
        return ResponseEntity.notFound().build();
    }
}
