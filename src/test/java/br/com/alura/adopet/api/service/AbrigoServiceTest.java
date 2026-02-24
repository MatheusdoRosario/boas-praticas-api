package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @InjectMocks
    private AbrigoService service;

    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private Abrigo abrigo;

    @Mock
    private Pet pet;

    @Mock
    private CadastroAbrigoDto dto;

    @Test
    void deveChamarUmaListaDeAbrigos() {

        service.listar();

        then(abrigoRepository).should().findAll();
    }

    @Test
    void deveCadastrarAbrigoSemErro() {

        given(abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email())).willReturn(false);

        Assertions.assertDoesNotThrow(() -> service.cadastrar(dto));
    }

    @Test
    void naoDeveCadastrarAbrigoComErro() {

        given(abrigoRepository.existsByNomeOrTelefoneOrEmail(dto.nome(), dto.telefone(), dto.email())).willReturn(true);

        Assertions.assertThrows(ValidacaoException.class,() -> service.cadastrar(dto));
    }

    @Test
    void deveChamarUmaListaDePetsDoAbrigo() {

        String nome = "Teste";
        given(abrigoRepository.findByNome(nome)).willReturn(Optional.of(abrigo));

        service.listarPetsDoAbrigo(nome);

        then(petRepository).should().findByAbrigo(abrigo);
    }


}