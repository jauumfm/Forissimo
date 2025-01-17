package com.ONE.Forissimo.infra.security;

import com.ONE.Forissimo.models.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component/*usado isso pra informar o spring para ele q isso e um compenente e abrir automaticamente*/
public class SecurityFilter extends OncePerRequestFilter {/*herdando uma classe q tem o flter*/

    @Autowired
    private TokenService tokenService;


    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request);/*criando cariavel com topken armazenado*/

        if (tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);
            /*isso recupera o email e chama o metodo para verificar token*/
            var usuario = repository.findByNome(subject);


            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        filterChain.doFilter(request, response);/*codigo feito para depois devalidado
        ir pro proximo filtro*/
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");/*lendo token do cabecalho*/

        if (authorizationHeader != null){/*verifica se cabecalho ta vazio*/
            return authorizationHeader.replace("Bearer ", "");
        }
            return null;

    }


}
