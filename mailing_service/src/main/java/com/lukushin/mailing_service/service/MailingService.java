package com.lukushin.mailing_service.service;

import com.lukushin.mailing_service.dto.MessageDTO;
import com.lukushin.mailing_service.entity.Email;
import com.lukushin.mailing_service.exception.FileException;
import com.lukushin.mailing_service.exception.MailingException;
import com.lukushin.mailing_service.exception.NoContentException;
import com.lukushin.mailing_service.repository.EmailServiceRepository;
import com.lukushin.mailing_service.repository.MessageServiceRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Service
public class MailingService {

    @Value("${spring.mail.username}")
    private String emailFrom;

    private final EmailServiceRepository emailServiceRepository;
    private final MessageServiceRepository messageServiceRepository;
    private final JavaMailSender javaMailSender;

    @Autowired
    public MailingService(EmailServiceRepository emailServiceRepository,
                          MessageServiceRepository messageServiceRepository,
                          JavaMailSender javaMailSender) {
        this.emailServiceRepository = emailServiceRepository;
        this.messageServiceRepository = messageServiceRepository;
        this.javaMailSender = javaMailSender;
    }

    // Запускает рассылку с интервалом в 30 минут
    public void startMailing() {
        log.debug("Start mailing");
        List<Email> unsentEmails = emailServiceRepository.findByIsEmailSentFalse();
        List<MessageDTO> messages = messageServiceRepository.findAll();

        if (unsentEmails.isEmpty()) {
            throw new NoContentException("There are no addresses for mailing in the database " +
                    "or all addresses have already been used in mailing");
        } else if (messages.isEmpty()) {
            throw new NoContentException("There is no `messages` in database");
        }

        for (Email email : unsentEmails) {

            if (messages.isEmpty()){
                messages = messageServiceRepository.findAll();
            }

            var now = LocalTime.now(ZoneId.of("Europe/Moscow"));

            while (isNotMailingTime(now)){
                waitForNextTimeCheck();
                now = LocalTime.now(ZoneId.of("Europe/Moscow"));
            }

            var message = getRandomMessageAndRemove(messages);

//            FIXME -------------------------------
//            sendMessage(email.getEmail(), message);
//            ~~~~~~~~~~~
            System.out.println("Sent email: " + message.getSubject() + " to " + email.getEmail());
//            FIXME -------------------------------


            email.setEmailSent(true);
            emailServiceRepository.save(email);
            waitForSendNextEmail();
        }
        log.debug("There are no more addresses in the database");
    }

    // проверка текущего времени на нахождение в заданном интервале
    private boolean isNotMailingTime(LocalTime time) {
        var startMailing = LocalTime.of(9,0);
        var endMailing = LocalTime.of(18, 0);
        return time.isBefore(startMailing) || time.isAfter(endMailing);
    }

    // останавливает поток на некоторое время перед следующей проверкой
    // на текущее время
    private void waitForNextTimeCheck() {
        try {
            Thread.sleep(300_000); // 5 минут
        } catch (InterruptedException e) {
            log.error("Error while waiting for mailing time: ", e);
            Thread.currentThread().interrupt();
        }
    }

    private void waitForSendNextEmail() {
        try {
            // TODO поменять время
//            Thread.sleep(1_800_000); // 30 минут
            Thread.sleep(2000); // 2 секунды
        } catch (InterruptedException e) {
            log.error("Error while waiting for sent next email: ", e);
            Thread.currentThread().interrupt();
        }
    }

    private MessageDTO getRandomMessageAndRemove(List<MessageDTO> messages) {
        int randomIndex = (int) (Math.random() * messages.size());
        return messages.remove(randomIndex);
    }

    // Метод отправляет письмо на указанную почту (с адреса пользователя)
    private void sendMessage(String emailTo, MessageDTO message){
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper;

            helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(emailFrom);
            helper.setTo(emailTo);
            helper.setSubject(message.getSubject());
            helper.setText(message.getText(), true);

            String attachedFilePath = message.getFilePath();
            if(attachedFilePath != null && !attachedFilePath.isEmpty()) {
                File file = new File(attachedFilePath);
                String attachedFileName = file.getName();
                helper.addAttachment(attachedFileName, file);
            }

            javaMailSender.send(mimeMessage);
            log.debug("Sent email: " + message.getSubject() + " to " + emailTo);
        } catch (MessagingException e) {
            throw new MailingException("Error sending email to: " + emailTo, e);
        } catch (MailSendException e) {
            throw new MailingException("Error mailing " + e.getMessage(), e);
        } catch (NullPointerException e) {
            throw new FileException("An error occurred while working with the file. " +
                    "The file was not found or the file path is incorrect.", e);
        }
    }

    // Метод отправляет одно рандомное письмо на почту пользователя
    public void sendTestMessage() throws NoContentException {
        var message = messageServiceRepository.findAny();
        log.debug("Send test message");
        sendMessage(emailFrom, message);
    }
}
