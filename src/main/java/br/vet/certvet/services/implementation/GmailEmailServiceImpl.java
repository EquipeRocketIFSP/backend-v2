package br.vet.certvet.services.implementation;

import br.vet.certvet.services.EmailService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
@NoArgsConstructor
public class GmailEmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendTextMessage(String to, String subject, String text) throws MessagingException {
        boolean isMultipart = true;
        String encoding = "UTF-8";
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, isMultipart, encoding);
        helper.setFrom(sender);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);

        emailSender.send(message);
    }


    @Override
    public void sendMessageWithAttachment(String to, String subject, String text, String attachmentName, String pathToAttachment) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
        helper.addAttachment(attachmentName, new FileSystemResource(new File(pathToAttachment)));

        emailSender.send(message);
    }
}
