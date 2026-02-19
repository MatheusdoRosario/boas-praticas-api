package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class PetService {

    @Autowired
    private PetRepository repository;

    public ResponseEntity<List<Pet>> listarTodosDisponiveis() {
        List<Pet> disponiveis = repository.findByAdotado(false);
        return ResponseEntity.ok(disponiveis);
    }
}
