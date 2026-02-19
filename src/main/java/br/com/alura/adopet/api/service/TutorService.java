package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizarTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoTutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class TutorService {

    @Autowired
    private TutorRepository repository;

    @Autowired
    List<ValidacaoTutor> validacoes;

    public ResponseEntity<String> cadastrar(CadastroTutorDto dto) {

        validacoes.forEach(v -> v.validar(dto));

        Tutor tutor = new Tutor(dto.nome(), dto.telefone(), dto.email());

            repository.save(tutor);
            return ResponseEntity.ok().build();
    }

    public ResponseEntity<String> atualizar(AtualizarTutorDto dto) {

        validacoes.forEach(v -> v.validar(dto));

        Tutor tutor = repository.findById(dto.id())
                .orElseThrow(() -> new RuntimeException("Tutor não encontrado"));

        tutor.atualizarTutor(dto);

        repository.save(tutor);
        return ResponseEntity.ok().build();
    }
}
