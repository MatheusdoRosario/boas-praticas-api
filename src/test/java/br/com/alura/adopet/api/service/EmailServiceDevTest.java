package br.com.alura.adopet.api.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmailServiceDevTest {

    @Mock
    private EmailServiceDev emailServiceDev;

    @Test
    void deveEnviarUmEmailSemErro() {

        Assertions.assertDoesNotThrow(() -> emailServiceDev.enviarEmail("Teste", "Teste", "Teste"));
    }

}