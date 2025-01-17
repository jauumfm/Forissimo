package com.ONE.Forissimo.infra.security;


import com.ONE.Forissimo.models.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")/*aplication. properties vai estar la*/
    private String secret;

    public String gerarToken(Usuario usuario) {/*esse usuario e so pra mostrar no getlogin*/
        try {

            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("Forissimo")/*api dona mostrado no token*/
                    .withSubject(usuario.getNome())/*adicionand ao token qual usuario*/
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerrar token jwt", exception);
        }
    }

    public String getSubject(String tokenJWT) {/*passa o token*/
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("Forissimo")/*verificando isuer*/
                    .build()
                    .verify(tokenJWT)/*verifica se o token e valido*/
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }
}
