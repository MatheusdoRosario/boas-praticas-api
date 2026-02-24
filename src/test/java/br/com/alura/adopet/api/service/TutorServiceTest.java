package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @InjectMocks
    private TutorService service;

    @Mock
    private TutorRepository repository;

    @Mock
    private CadastroTutorDto cadastroTutorDto;

    private AtualizacaoTutorDto atualizacaoTutorDto;
    private Tutor tutor;

    @Captor
    private ArgumentCaptor<Tutor> tutorArgumentCaptor;

    @Test
    void deveCadastrarTutorSemErro() {

        given(repository.existsByTelefoneOrEmail(cadastroTutorDto.telefone(), cadastroTutorDto.email())).willReturn(false);

        Assertions.assertDoesNotThrow(() -> service.cadastrar(cadastroTutorDto));
    }

    @Test
    void naoDeveCadastrarTutorComDadosCadastrados() {

        given(repository.existsByTelefoneOrEmail(cadastroTutorDto.telefone(), cadastroTutorDto.email())).willReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> service.cadastrar(cadastroTutorDto));
    }

    @Test
    void deveAtualizarTutorSemErro() {

        AtualizacaoTutorDto dto = new AtualizacaoTutorDto(1L, "Teste", "22555554444", "teste@gmail.com");
        Tutor tutor = new Tutor(new CadastroTutorDto("teste", "22555554444", "teste@outlook.com"));
        given(repository.getReferenceById(dto.id())).willReturn(tutor);

        service.atualizar(dto);

        Assertions.assertEquals("Teste", tutor.getNome());
        Assertions.assertEquals("22555554444", tutor.getTelefone());
        Assertions.assertEquals("teste@gmail.com", tutor.getEmail());

        //Assertions.assertDoesNotThrow(() -> tutor.atualizarDados(dto));
    }


}