package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @InjectMocks
    private PetService service;

    @Mock
    private PetRepository repository;

    @Mock
    private Pet pet;

    @Mock
    private PetDto petDto;

    @Mock
    private Abrigo abrigo;

    private CadastroPetDto cadastroPetDto;

    @Captor
    private ArgumentCaptor<Pet> petCaptor;


    @Test
    void deveBuscarOsPetsDisponiveis() {

        given(repository.findAllByAdotadoFalse()).willReturn(List.of(pet, pet, pet));

        List<PetDto> dtos = service.buscarPetsDisponiveis();

        assertEquals(3, dtos.size());
    }

    @Test
    void deveCadastrarPet() {

        CadastroPetDto cadastroPetDto = new CadastroPetDto(TipoPet.CACHORRO, "Rex", "BorderCollie", 10, "Preto e branco", 10.0f);

        service.cadastrarPet(abrigo, cadastroPetDto);

        then(repository).should().save(petCaptor.capture());
        Pet petSalvo = petCaptor.getValue();

        Assertions.assertEquals("Rex", petSalvo.getNome());
        Assertions.assertEquals("BorderCollie", petSalvo.getRaca());
        Assertions.assertEquals(10, petSalvo.getIdade());
        Assertions.assertEquals(abrigo, petSalvo.getAbrigo());

    }


}