package br.com.alura.adopet.api.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@ExtendWith(MockitoExtension.class)
class EmailServiceProducaoTest {

    @Mock
    private JavaMailSender emailSender;

    @Test
    void deveEnviarEmailSemErro(){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("Teste");
        email.setTo(new String[]{"Teste@gmail.com"});
        email.setSubject("Teste");
        email.setText("Teste");

        Assertions.assertDoesNotThrow(() -> emailSender.send(email));
    }

}