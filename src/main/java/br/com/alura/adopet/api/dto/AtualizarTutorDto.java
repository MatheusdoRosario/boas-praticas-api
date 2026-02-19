package br.com.alura.adopet.api.dto;

import jakarta.validation.constraints.NotNull;

public record AtualizarTutorDto(@NotNull Long id,
                                String nome,
                                String telefone,
                                String email) {
}
