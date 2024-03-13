package com.lukushin.view_service.controller;

import com.lukushin.view_service.repository.MailingServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mailing")
public class MailingController {

    private final MailingServiceRepository mailingServiceRepository;

    @Autowired
    public MailingController(MailingServiceRepository mailingServiceRepository) {
        this.mailingServiceRepository = mailingServiceRepository;
    }

    @GetMapping()
    public String mailingSetup(){
        return "/mailing";
    }

    @PostMapping("/testMail")
    public String sendTestMail(Model model){
        boolean isTestMessageSend = mailingServiceRepository.sendTestMessage();
        model.addAttribute("testMessageIsSend", isTestMessageSend);
        return "/mailing";
    }

    @PostMapping ("/startMailing")
    public String startMailing(Model model){
        boolean isMailingStarted = mailingServiceRepository.startMailing();
        model.addAttribute("mailingIsStart", isMailingStarted);
        return "redirect:/mailing";
    }

}