package com.lukushin.email_service.service;

import com.lukushin.email_service.entity.Email;
import com.lukushin.email_service.exception.NoSuchEmailException;
import com.lukushin.email_service.repository.EmailRepository;
import com.lukushin.email_service.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
public class EmailService {

    private final EmailRepository emailRepository;
    private final FileUtils fileUtils;

    @Autowired
    public EmailService(EmailRepository emailRepository,
                        FileUtils fileUtils) {
        this.emailRepository = emailRepository;
        this.fileUtils = fileUtils;
    }

    public List<Email> findAllEmails() {
        log.info("Retrieving all emails");
        return emailRepository.findAll();
    }

    public boolean saveEmails(MultipartFile XLSXFile) {
        log.info("Saving emails from file {}", XLSXFile.getOriginalFilename());
        var emails = fileUtils.parseXLSXFile(XLSXFile);

        if (emails.isEmpty()) {
            log.warn("No emails found in the file");
            return false;
        }

        emails.stream()
                .filter(email -> !emailRepository.existsByEmail(email))
                .forEach(email -> emailRepository.save(new Email(email)));
        log.info("Emails save successfully");
        return true;
    }

    public void deleteEmail(String email) {
        log.info("Deleting email: {}", email);
        var optional = emailRepository.findByEmail(email);

        if (optional.isEmpty()) {
            throw new NoSuchEmailException("the email: "
                    + email + " does not exist in the database");
        }

        emailRepository.deleteById(optional.get().getId());
        log.info("Email: {} deleted", email);
    }

    public void deleteAll() {
        log.info("Deleting all emails");
        emailRepository.deleteAll();
        log.info("All emails deleted");
    }

    public void resetSentStatus() {
        log.info("Resetting sent status for all emails");
        emailRepository.resetSentStatus();
        log.info("Sent status reset successfully");
    }
}
