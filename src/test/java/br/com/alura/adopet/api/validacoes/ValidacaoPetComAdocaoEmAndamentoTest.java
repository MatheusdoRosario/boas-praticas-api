package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetComAdocaoEmAndamentoTest {

    @InjectMocks
    private ValidacaoPetComAdocaoEmAndamento validacao;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Mock
    private Adocao adocao;

    @Mock
    private AdocaoRepository repository;

    @Test
    void deveriaPermitirValidacaoPetComAdocaoEmAndamento() {

        BDDMockito.given(dto.idPet()).willReturn(1L);
        BDDMockito.given(repository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(false);

        Assertions.assertDoesNotThrow(() -> validacao.validar(dto));

    }

    @Test
    void naoDeveriaPermitirValidacaoPetComAdocaoEmAndamento() {

        BDDMockito.given(dto.idPet()).willReturn(1L);
        BDDMockito.given(repository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(true);

        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }

}