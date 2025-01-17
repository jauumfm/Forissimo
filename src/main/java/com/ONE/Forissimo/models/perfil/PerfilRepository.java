package com.ONE.Forissimo.models.perfil;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.ScopedValue;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
}
