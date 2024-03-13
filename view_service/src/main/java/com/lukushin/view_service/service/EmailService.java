package com.lukushin.view_service.service;

import com.lukushin.view_service.repository.EmailServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmailService {

    private final EmailServiceRepository emailServiceRepository;

    @Autowired
    public EmailService(EmailServiceRepository emailServiceRepository) {
        this.emailServiceRepository = emailServiceRepository;
    }

    public void saveEmails(MultipartFile file) {
            emailServiceRepository.saveEmails(file);
    }

    public void deleteAllEmails() {
        emailServiceRepository.deleteAll();
    }

    public void resetSentStatus() {
        emailServiceRepository.resetSentStatus();
    }
}
