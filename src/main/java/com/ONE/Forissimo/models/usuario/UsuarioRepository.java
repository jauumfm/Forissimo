package com.ONE.Forissimo.models.usuario;

import aj.org.objectweb.asm.commons.Remapper;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByNome(String nome);

    Page<Usuario> findAllByAtivoTrue(Pageable paginacao);

    boolean existsByNome(String nome);

    boolean existsByEmail(@NotBlank @Email String email);
}
