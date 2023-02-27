package br.vet.certvet.services;

import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public interface EmailService {

    void sendTextMessage(String to, String subject, String text);

    void sendMessageWithAttachment(String to, String subject, String text, String attachmentName, String pathToAttachment) throws MessagingException;

}
