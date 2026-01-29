package com.mycroservice.emailsender.services;

import com.mycroservice.emailsender.dto.EmailRequestDto;
import com.mycroservice.emailsender.infrastructure.EmailSenderPort;
import com.mycroservice.emailsender.persistence.EmailRepository;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private EmailSenderPort sender;
    @Mock
    private EmailRepository repository;
    @InjectMocks
    @Spy
    private EmailService service;

    @Captor
    private ArgumentCaptor<MimeMessage> mimeMessageCaptor;

    @Nested
    class send {

        @Test
        @DisplayName("Should send an email with success with html body")
        void shouldSendEmailSuccessHtmlBody() throws Exception {
            // Arrange
            var emailDto = new EmailRequestDto(
                    List.of("pedro.mape7@gmail.com"),
                    "subject",
                    "body"
            );
            var emailContent = "<!DOCTYPE html><html><head></head><body>Oi</body></html>";
            MultipartFile html = new MockMultipartFile(
                    "html",
                    "doc.html",
                    "text/html",
                    emailContent.getBytes(StandardCharsets.UTF_8)
            );
            var session = Session.getInstance(new Properties());
            when(sender.createMimeMessage()).thenReturn(new MimeMessage(session));
            doNothing().when(sender).send(mimeMessageCaptor.capture());
            doReturn(UUID.randomUUID()).when(service).save(any());

            // Act
            assertNotNull(service.send(emailDto, html));

            // Assert
            var messageCaptured = mimeMessageCaptor.getValue();
            messageCaptured.saveChanges();
            assertEquals(emailDto.to().getFirst(), messageCaptured.getAllRecipients()[0].toString());
            assertEquals(emailDto.subject(), messageCaptured.getSubject());

//            var bodyPart = ((MimeMultipart) messageCaptured.getContent()).getBodyPart(0);
//            assertTrue(bodyPart.getContent().toString().contains("<body>Oi</body>"));
            var multipart = (MimeMultipart) messageCaptured.getContent();
            var alternativePart = (MimeBodyPart) multipart.getBodyPart(0);
            var alternativeMultipart = (MimeMultipart) alternativePart.getContent();
            var htmlPart = (MimeBodyPart) alternativeMultipart.getBodyPart(0);
            var htmlContent = htmlPart.getContent().toString();

            assertTrue(htmlContent.contains("<body>Oi</body>"));

            verify(service).save(emailDto);
        }

    }
}