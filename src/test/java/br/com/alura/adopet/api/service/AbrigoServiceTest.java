package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AbrigoDto;
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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
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
    private AbrigoDto abrigoDto;

    @Mock
    private PetDto petDto;

    @Mock
    private Abrigo abrigo;

    @Mock
    private Pet pet;

    @Mock
    private CadastroAbrigoDto dto;

    @Mock
    private ValidacaoException validacaoException;

    @Test
    void deveRetornarUmaListaDeAbrigoDto() {

        given(abrigoRepository.findAll()).willReturn(List.of(abrigo, abrigo));

        List<AbrigoDto> dtos = service.listar();

        assertEquals(2, dtos.size());
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
    void deveRetornarUmaListaDePetsDoAbrigo() {

        given(abrigoRepository.findByNome("Teste")).willReturn(Optional.of(abrigo));
        given(petRepository.findByAbrigo(abrigo)).willReturn(List.of(pet, pet, pet));

        List<PetDto> dtos = service.listarPetsDoAbrigo("Teste");

        assertEquals(3, dtos.size());
    }

    @Test
    void deveCarregarAbrigoPeloIdSemErro() {
        String id = "1";
        given(abrigoRepository.findById(Long.parseLong(id))).willReturn(Optional.of(abrigo));

        Assertions.assertDoesNotThrow(() -> service.carregarAbrigo(id));
    }

    @Test
    void naoDeveCarregarAbrigoPeloIdComErro() {
        String id = "1";
        given(abrigoRepository.findById(Long.parseLong(id))).willReturn(Optional.empty());

        Assertions.assertThrows(ValidacaoException.class,() -> service.carregarAbrigo(id));

    }

    @Test
    void deveCarregarAbrigoPeloNomeSemErro() {
        String nome = "Teste";
        given(abrigoRepository.findByNome(nome)).willReturn(Optional.of(abrigo));

        Assertions.assertDoesNotThrow(() -> service.carregarAbrigo(nome));
    }

    @Test
    void naoDeveCarregarAbrigoPeloNomeComErro() {

        String nome = null;
        given(abrigoRepository.findByNome(nome)).willReturn(Optional.empty());

        Assertions.assertThrows(ValidacaoException.class,() -> service.carregarAbrigo(nome));
    }


}