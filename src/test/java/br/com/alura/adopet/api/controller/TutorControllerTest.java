package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.service.TutorService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TutorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TutorService service;

    @Autowired
    private JacksonTester<CadastroTutorDto> cadastroJson;

    @Autowired
    private JacksonTester<AtualizacaoTutorDto> atualizacaoJson;

    @Test
    void deveriaRetornarCodigo200AoCadastrarTutorSemErro() throws Exception {

        CadastroTutorDto cadastroTutorDto = new CadastroTutorDto("Teste", "43555554444", "teste@gmail.com");

        var response = mvc.perform(
                post("/tutores")
                        .content(cadastroJson.write(cadastroTutorDto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornarCodigo400AoCadastrarTutorComErro() throws Exception {

        var response = mvc.perform(
                post("/tutores")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deveriaRetornarCodigo200AoAtualizarTutorSemErro() throws Exception {

        AtualizacaoTutorDto atualizacaoTutorDto = new AtualizacaoTutorDto(1L, "Teste", "43555554444", "teste@gmail.com");

        var response = mvc.perform(
                put("/tutores")
                        .content(atualizacaoJson.write(atualizacaoTutorDto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void deveriaRetornarCodigo400AoAtualizarTutorComErro() throws Exception {

        var response = mvc.perform(
                put("/tutores")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

}