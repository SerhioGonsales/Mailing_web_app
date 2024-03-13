package com.lukushin.email_service.controller;

import com.lukushin.email_service.entity.Email;
import com.lukushin.email_service.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/emails")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    public ResponseEntity<List<Email>> getAllEmails() {
        return ResponseEntity.ok(emailService.findAllEmails());
    }

    @GetMapping("/resetSentStatus")
    public ResponseEntity<String> resetSentStatus() {
        emailService.resetSentStatus();
        return ResponseEntity.ok("all \"sent\" values have been reset");
    }

    @PostMapping
    public ResponseEntity<String> addNewEmails(@RequestParam ("XLSXFile") MultipartFile XLSXFile) {
        if(emailService.saveEmails(XLSXFile)) {
            return ResponseEntity.ok("Emails have been added to the database");
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllEmails() {
        emailService.deleteAll();
        return ResponseEntity.ok("all emails was successfully deleted from database");
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteEmail(@PathVariable ("email") String email) {
        emailService.deleteEmail(email);
        return ResponseEntity.ok("the email: " + email + " was successfully deleted");
    }
}
