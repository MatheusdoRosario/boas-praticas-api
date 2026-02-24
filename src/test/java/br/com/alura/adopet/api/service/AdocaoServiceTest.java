package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.*;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;


@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

    @InjectMocks
    private AdocaoService service;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private EmailService emailService;

    @Spy
    private List<ValidacaoSolicitacaoAdocao> validacoes = new ArrayList<>();

    @Mock
    private ValidacaoSolicitacaoAdocao validador1;

    @Mock
    private ValidacaoSolicitacaoAdocao validador2;

    @Mock
    private Pet pet;

    @Mock
    private Tutor tutor;

    @Mock
    private Abrigo abrigo;

    @Mock
    private Adocao adocao;

    private SolicitacaoAdocaoDto dto;
    private AprovacaoAdocaoDto aprovacaoDto;
    private ReprovacaoAdocaoDto reprovacaoDto;

    @Captor
    private ArgumentCaptor<Adocao> adocaoArgumentCaptor;

    @Test
    void deveriaSalvarAdocaoAoSolicitar(){

        this.dto = new SolicitacaoAdocaoDto(10l, 20l, "Motivo qualquer");

        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);

        service.solicitar(dto);

        then(adocaoRepository).should().save(adocaoArgumentCaptor.capture());
        Adocao adocaoSalva = adocaoArgumentCaptor.getValue();
        Assertions.assertEquals(pet, adocaoSalva.getPet());
        Assertions.assertEquals(tutor, adocaoSalva.getTutor());
        Assertions.assertEquals(dto.motivo(), adocaoSalva.getMotivo());

    }

    @Test
    void deveriaChamarValidadoresAdocaoAoSolicitar(){

        this.dto = new SolicitacaoAdocaoDto(10l, 20l, "Motivo qualquer");

        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);
        validacoes.add(validador1);
        validacoes.add(validador2);

        service.solicitar(dto);

        then(validador1).should().validar(dto);
        then(validador2).should().validar(dto);
    }

    @Test
    void deveriaSalvarAdocaoAoAprovar() {

        this.aprovacaoDto = new AprovacaoAdocaoDto(1L);

        given(adocaoRepository.getReferenceById(aprovacaoDto.idAdocao())).willReturn(adocao);
        given(adocao.getPet()).willReturn(pet);
        given(adocao.getTutor()).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);
        given(tutor.getNome()).willReturn("Fulano");
        given(adocao.getData()).willReturn(LocalDateTime.now());

        Mockito.doAnswer(invocation -> {
            given(adocao.getStatus()).willReturn(StatusAdocao.APROVADO);
            return null;
        }).when(adocao).marcarComoAprovada();

        service.aprovar(aprovacaoDto);

        then(adocao).should().marcarComoAprovada();

        Assertions.assertEquals(StatusAdocao.APROVADO, adocao.getStatus());
    }

    @Test
    void deveriaSalvarAdocaoAoReprovar() {

        this.reprovacaoDto = new ReprovacaoAdocaoDto(1L, "Motivo Qualquer");

        given(adocaoRepository.getReferenceById(reprovacaoDto.idAdocao())).willReturn(adocao);
        given(adocao.getPet()).willReturn(pet);
        given(adocao.getTutor()).willReturn(tutor);
        given(pet.getAbrigo()).willReturn(abrigo);
        given(tutor.getNome()).willReturn("Fulano");
        given(adocao.getData()).willReturn(LocalDateTime.now());

        Mockito.doAnswer(invocation -> {
            given(adocao.getStatus()).willReturn(StatusAdocao.REPROVADO);
            return null;
        }).when(adocao).marcarComoReprovada(reprovacaoDto.justificativa());

        service.reprovar(reprovacaoDto);

        then(adocao).should().marcarComoReprovada(reprovacaoDto.justificativa());

        Assertions.assertEquals(StatusAdocao.REPROVADO, adocao.getStatus());
    }


}