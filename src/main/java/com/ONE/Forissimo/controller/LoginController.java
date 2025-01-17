package com.ONE.Forissimo.controller;


import com.ONE.Forissimo.infra.security.DadosTokenJWT;
import com.ONE.Forissimo.models.perfil.PerfilRepository;
import com.ONE.Forissimo.models.usuario.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import com.ONE.Forissimo.infra.security.TokenService;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    /*invocand o objeto q trabalha com token*/
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    /*CADASTRANDO*/
    @PostMapping("/cadastrar")
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastro dados, UriComponentsBuilder uriBuilder) {
        /*UriComponentsBuilder CRIA A PRIMEIRA PARTE DO LINK ESCONDENDO O MSM */
        var perfil = perfilRepository.findById(dados.perfilId())/*verificando se o perfil existe*/
                .orElseThrow(() -> new IllegalArgumentException("Perfil não encontrado"));
        var user = new Usuario(dados, perfil);
        user.setSenha(passwordEncoder.encode(dados.senha()));
        repository.save(user);
        /*ESSE CODIGO RETORNA 201 Q EXIGE TAMBEM O Q FOI CADASTRAO E UM CABECALHO COM URI*/
        var uri = uriBuilder.path("login/{id}").buildAndExpand(user.getId()).toUri();
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

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));/*a resposta vira um DadosTokenTJWT*/
    }

    /*LISTANDO USUARIOS*/
    @GetMapping
    public ResponseEntity<Page<DadosListagemUsuario>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAll(paginacao)
                .map(DadosListagemUsuario::new);
        return ResponseEntity.ok(page);/*retorna o 200 mais as infos q estao em page*/
    }

    /*LISTANDO APENAS UM USUARIO*/
    @GetMapping("/{id}")/*detalhes dos usuarios*/
    public ResponseEntity detalhar(@PathVariable Long id) {
        var usuario = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamento(usuario));
    }


}

