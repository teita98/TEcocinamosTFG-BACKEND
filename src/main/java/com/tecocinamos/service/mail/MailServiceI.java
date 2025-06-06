package com.tecocinamos.service.mail;

public interface MailServiceI {
    void sendPlainTextEmail(String to, String subject, String text);
    void sendHtmlEmail(String to, String subject, String htmlBody);
    void sendEmailWithAttachment(String to, String subject, String htmlBody, String filename, byte[] attachmentBytes);
}
