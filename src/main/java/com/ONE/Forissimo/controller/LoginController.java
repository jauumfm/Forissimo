package com.ONE.Forissimo.controller;


import com.ONE.Forissimo.models.usuario.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.token.TokenService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    /*invocand o objeto q trabalha com token*/
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    /*CADASTRANDO*/
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastro dados, UriComponentsBuilder uriBuilder) {
        /*UriComponentsBuilder CRIA A PRIMEIRA PARTE DO LINK ESCONDENDO O MSM */
        var user = new Usuario(dados);
        repository.save(user);
        /*ESSE CODIGO RETORNA 201 Q EXIGE TAMBEM O Q FOI CADASTRAO E UM CABECALHO COM URI*/
        var uri = uriBuilder.path("/usuario/{id}").buildAndExpand(user.getId()).toUri();
        /*CAMINHO BASCIO LOCALHOST(COMPLEMENTO DO CAMINHO).PEGA O ID Q  FOI CRIADO AGOPRA.TRANSFORMA EM URI*/
        return ResponseEntity.created(uri).body(new DadosDetalhamento(user));
    }

    /*FAZENDO LOGIN*/
    @PostMapping
    /*efetuarLogin recebe DTO com campos login e senha*/
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {

        /*transformando o nosso dto para DTO do spring(usando as infos de dados)*/
        var token = new UsernamePasswordAuthenticationToken(dados.nome(), dados.senha());
        /*authentication é o objeto que representa o usuario aitenticado */
        var authentication = manager.authenticate(token);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return null;//ResponseEntity.ok(new DadosTokenJWT(tokenJWT));/*a resposta vira um DadosTokenTJWT*/
    }

}

