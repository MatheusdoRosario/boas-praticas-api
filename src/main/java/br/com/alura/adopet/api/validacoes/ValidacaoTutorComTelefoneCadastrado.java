package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.AtualizarTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidacaoTutorComTelefoneCadastrado implements ValidacaoTutor {

    @Autowired
    private TutorRepository repository;

    @Override
    public void validar(CadastroTutorDto dto) {
        boolean telefoneJaCadastrado = repository.existsByTelefone(dto.telefone());
    }

    @Override
    public void validar(AtualizarTutorDto dto) {
        boolean telefoneJaCadastrado = repository.existsByTelefone(dto.telefone());    }
}
