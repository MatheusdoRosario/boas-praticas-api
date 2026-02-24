package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.exception.AbrigoNaoEncontradoException;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AbrigoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AbrigoService abrigoService;

    @MockBean
    private PetService petService;

    @Autowired
    private JacksonTester<CadastroAbrigoDto> cadastroAbrigoJson;

    @Autowired
    private JacksonTester<CadastroPetDto> cadastroPetJson;

    @Test
    void deveriaRetornarCodigo200AoListarAbrigo() throws Exception {

        var response = mvc.perform(
                get("/abrigos")
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornarCodigo200AoCadastrarAbrigoSemErro() throws Exception {

        CadastroAbrigoDto dto = new CadastroAbrigoDto("Teste", "43555554444", "teste@gmail.com");

        var response = mvc.perform(
                post("/abrigos")
                        .content(cadastroAbrigoJson.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornarCodigo400AoCadastrarAbrigoComErro() throws Exception {

        var response = mvc.perform(
                post("/abrigos")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornarCodigo200AoBuscarPetsSemErro() throws Exception {

        var response = mvc.perform(
                get("/abrigos/1/pets")
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornarCodigo404AoBuscarPetsComErro() throws Exception {
        when(abrigoService.listarPetsDoAbrigo("1")).thenThrow(new AbrigoNaoEncontradoException(""));

        var response = mvc.perform(
                get("/abrigos/1/pets")
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    void deveriaRetornarCodigo200AoCadastrarPetSemErro() throws Exception {

        CadastroPetDto dto = new CadastroPetDto(TipoPet.CACHORRO , "teste1", "teste2", 10,"preto", 5.0f);

        var response = mvc.perform(
                post("/abrigos/1/pets")
                        .content(cadastroPetJson.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornarCodigo404AoCadastrarPetComErro() throws Exception {
        when(abrigoService.carregarAbrigo("1")).thenThrow(new AbrigoNaoEncontradoException(""));
        CadastroPetDto dto = new CadastroPetDto(TipoPet.CACHORRO , "teste1", "teste2", 10,"preto", 5.0f);

        var response = mvc.perform(
                post("/abrigos/1/pets")
                        .content(cadastroPetJson.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

}