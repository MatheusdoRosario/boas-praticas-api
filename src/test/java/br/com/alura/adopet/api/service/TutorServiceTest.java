package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    private TutorService service;

    @Mock
    private TutorRepository repository;

    @Mock
    private CadastroTutorDto cadastroTutorDto;

    @Mock
    private AtualizacaoTutorDto atualizacaoTutorDto;

    @Mock
    private Tutor tutor;

    @Test
    void deveCadastrarTutorSemErro() {

        given(repository.existsByTelefoneOrEmail(cadastroTutorDto.telefone(), cadastroTutorDto.email())).willReturn(false);

        assertDoesNotThrow(() -> service.cadastrar(cadastroTutorDto));
        then(repository).should().save(new Tutor(cadastroTutorDto));
    }

    @Test
    void naoDeveCadastrarTutorComDadosCadastrados() {

        given(repository.existsByTelefoneOrEmail(cadastroTutorDto.telefone(), cadastroTutorDto.email())).willReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> service.cadastrar(cadastroTutorDto));
    }

    @Test
    void deveAtualizarTutorSemErro() {

        given(repository.getReferenceById(atualizacaoTutorDto.id())).willReturn(tutor);

        service.atualizar(atualizacaoTutorDto);

        then(tutor).should().atualizarDados(atualizacaoTutorDto);
    }


}