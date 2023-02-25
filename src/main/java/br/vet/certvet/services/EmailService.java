package br.vet.certvet.services;

import javax.mail.MessagingException;

public interface EmailService {

    void sendTextMessage(String to, String subject, String text);

    void sendMessageWithAttachment(String to, String subject, String text, String attachmentName, String pathToAttachment) throws MessagingException;

}
