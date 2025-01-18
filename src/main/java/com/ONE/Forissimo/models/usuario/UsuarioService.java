//package com.ONE.Forissimo.models.usuario;
//
//
//import com.ONE.Forissimo.infra.exception.ValidacaoException;
//import com.ONE.Forissimo.models.perfil.PerfilRepository;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UsuarioService {
//
//    @Autowired
//    UsuarioRepository repository;
//
//    @Autowired
//    PerfilRepository perfilRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    public void cadastrando(@Valid DadosCadastro dados) {
//
//        var checandoNome = repository.existsByNome(dados.nome());
//        var checandoEmail = repository.existsByNome(dados.email());
//
//        if (dados.nome().equalsIgnoreCase("usuario desativado")){
//            throw new ValidacaoException("Usuario excluido ou desativado");
//        }
//        if (checandoNome){
//            throw new ValidacaoException("Usuario ja existe");
//        }
//        if (checandoEmail){
//            throw new ValidacaoException("Email ja cadastrado");
//        }
//        var perfil = perfilRepository.findById(dados.perfilId())/*verificando se o perfil existe*/
//                .orElseThrow(() -> new ValidacaoException("Perfil não encontrado"));
//        var user = new Usuario(dados, perfil);
//        user.setSenha(passwordEncoder.encode(dados.senha()));/*encodando a senha*/
//        repository.save(user);
//        /*ESSE CODIGO RETORNA 201 Q EXIGE TAMBEM O Q FOI CADASTRAO E UM CABECALHO COM URI*/
//        var uri = uriBuilder.path("login/{id}").buildAndExpand(user.getId()).toUri();
//        /*CAMINHO BASCIO LOCALHOST(COMPLEMENTO DO CAMINHO).PEGA O ID Q  FOI CRIADO AGOPRA.TRANSFORMA EM URI*/
//    }
//}
