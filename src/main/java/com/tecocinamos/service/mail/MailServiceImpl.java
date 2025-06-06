package com.tecocinamos.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailServiceI {

    private final JavaMailSender javaMailSender;
    private final String fromEmail;

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender,
                           @Value("${spring.mail.username}") String fromEmail) {
        this.javaMailSender = javaMailSender;
        this.fromEmail = fromEmail;
    }


    /**
     * Envía un email de texto plano.
     */
    @Override
    public void sendPlainTextEmail(String to, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(text);
        javaMailSender.send(msg);
    }

    /**
     * Envía un email con HTML en el cuerpo.
     */
    @Override
    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            // false = no multipart, puesto que aquí no estamos adjuntando ficheros
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom(fromEmail);
            helper.setText(htmlBody, true); // true = es HTML
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Error al construir el email HTML", e);
        }
    }
}
