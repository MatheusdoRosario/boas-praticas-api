package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.AtualizarTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;

public interface ValidacaoTutor {

    void validar(CadastroTutorDto dto);

    void validar(AtualizarTutorDto dto);
}
