package com.ONE.Forissimo.controller;


import com.ONE.Forissimo.infra.exception.ValidacaoException;
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

import java.util.Optional;

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
        var checandoNome = repository.existsByNome(dados.nome());
        var checandoEmail = repository.existsByEmail(dados.email());

        if (checandoNome){
            throw new ValidacaoException("Usuario ja existe");
        }
        if (checandoEmail){
            throw new ValidacaoException("Email ja cadastrado");
        }

        var perfil = perfilRepository.findById(dados.perfilId())/*verificando se o perfil existe*/
                .orElseThrow(() -> new ValidacaoException("Perfil não encontrado"));

        var user = new Usuario(dados, perfil);
        user.setSenha(passwordEncoder.encode(dados.senha()));/*encodando a senha*/
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

        if (dados.nome().equalsIgnoreCase("usuario desativado")){
            throw new ValidacaoException("Usuario excluido ou desativado ou nao existe");
        }
        boolean usuarioAtivo = repository.existsByNome(dados.nome());/*verificando se usuario esta ativado*/
        if (!usuarioAtivo){
            throw new ValidacaoException("Usuario excluido ou desativado");
        }
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
        var page = repository.findAllByAtivoTrue(paginacao)
                .map(DadosListagemUsuario::new);
        return ResponseEntity.ok(page);/*retorna o 200 mais as infos q estao em page*/
    }

    /*LISTANDO APENAS UM USUARIO*/
    @GetMapping("/{id}")/*detalhes dos usuarios*/
    public ResponseEntity detalhar(@PathVariable Long id) {
        var usuario = repository.getReferenceById(id);
        if(!usuario.getAtivo()){
            throw new ValidacaoException("Usuario excluido ou desativado");
        }
        return ResponseEntity.ok(new DadosDetalhamento(usuario));
    }

    /*EDITANDO USUARIO*/
    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoUsuario dados) {
        var user = repository.getReferenceById(dados.id());
        if(!user.getAtivo()){
            throw new ValidacaoException("Usuario excluido ou desativado");
        }
        user.atualizarUsuario(dados);
        return ResponseEntity.ok(new DadosDetalhamento(user));}

    /*DESATIVANDO USUARIO*/
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        var user = repository.getReferenceById(id);
        if(!user.getAtivo()){
            throw new ValidacaoException("Usuario excluido ou desativado");
        }
        user.excluir();
        /*Response entity e para dar resposta para aquisicoa na web*/
        return ResponseEntity.noContent().build();
        /*tem mteodos estaticos no ResponseEnrity, porem nocontent n cria um objeto e o build faz isso*/
    }
}



