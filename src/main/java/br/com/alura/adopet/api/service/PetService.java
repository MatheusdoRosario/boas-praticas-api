package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.CadastrarPetDto;
import br.com.alura.adopet.api.dto.PetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PetService {

    @Autowired
    private PetRepository repository;

    public List<PetDto> listarTodosDisponiveis() {
        return repository
                .findAllByAdotadoFalse()
                .stream()
                .map(PetDto::new)
                .toList();
    }
    public void cadastrarPet(Abrigo abrigo, CadastrarPetDto dto){
        repository.save(new Pet());
    }
}
