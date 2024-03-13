package com.lukushin.mailing_service.controller;

import com.lukushin.mailing_service.exception.NoContentException;
import com.lukushin.mailing_service.service.MailingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mailing")
public class MailingController {

    private final MailingService mailingService;

    @Autowired
    public MailingController(MailingService mailingService) {
        this.mailingService = mailingService;
    }

    @GetMapping
    public ResponseEntity<?> startMailing() {
        try {
            mailingService.startMailing();
            return ResponseEntity.ok("Mailing has started!");
        } catch (NoContentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The mailing was not start " +
                    "because templates for letters were not found. Error: " + e.getMessage());
        }
    }

    @GetMapping ("/test")
    public ResponseEntity<?> sendTestMessage() {
        try {
            mailingService.sendTestMessage();
            return ResponseEntity.ok("A test message has been sent to your email");
        } catch (NoContentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The test message was not sent " +
                    "because templates for letters were not found. Error: " + e.getMessage());
        }
    }
}
