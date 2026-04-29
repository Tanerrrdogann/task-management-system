package com.gorevyonetimi.service;

import com.gorevyonetimi.model.Task;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MailServiceTest {

    @Test
    void shouldSendEmailSuccessfully() {
        JavaMailSender sender = Mockito.mock(JavaMailSender.class);
        MimeMessage message = Mockito.mock(MimeMessage.class);
        Mockito.when(sender.createMimeMessage()).thenReturn(message);

        MailService mailService = new MailService(sender);
        Task task = new Task("title", "desc", "Yüksek", "test@example.com", null);

        assertDoesNotThrow(() -> mailService.sendTaskReminderEmail(task));
    }
}