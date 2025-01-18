package com.ONE.Forissimo.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {/*trata erro quando o ID nao foi encontrado*/
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException ex) {/*trata erro quando o ID nao foi encontrado*/
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    private record DadosErroValidacao(String campo,
                                      String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
            /*pega apenas o campo e a mensagem do erro neese campo e cria um objeto*/
        }
/*DTO feito para tratamento de erro*/
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
        // Verifica se o erro é relacionado ao usuário não encontrado ou desativado
        if (ex.getMessage() != null && ex.getMessage().contains("usuario")) {
            return new ResponseEntity<>("Usuário desativado ou não encontrado", HttpStatus.FORBIDDEN); // 403 Forbidden
        }

        // Para outros NullPointerExceptions, você pode retornar um erro genérico ou outro comportamento
        return new ResponseEntity<>("Erro interno do servidor", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)/*quando der erro de validacao*/
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();

        return ResponseEntity.badRequest().body(erros
                .stream()/*pega os erros*/
                .map(DadosErroValidacao::new)/*mapeia criando um objeto com cada erro*/
                .toList());/*faz uma lista de objetos*/
/*o body e pra mostrar algo pro usuario*/
    }

}
