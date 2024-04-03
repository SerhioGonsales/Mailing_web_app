package com.lukushin.view_service.controller;

import com.lukushin.view_service.service.EmailService;
import com.lukushin.view_service.util.ViewUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class EmailsController {

    private final ViewUtils viewUtils;
    private final EmailService emailService;

    @Autowired
    public EmailsController(ViewUtils viewUtils, EmailService emailService) {
        this.viewUtils = viewUtils;
        this.emailService = emailService;
    }

    @GetMapping("/emails")
    public String emails(Model model){
        viewUtils.getEmailStatisticsForEmailsPage(model);
        return "emails";
    }

    @PostMapping("/uploadXSLX")
    public String saveEmails(
            @RequestParam("attachedXSLX") MultipartFile attachedXSLX,
            Model model){
        if(attachedXSLX.isEmpty()) {
            model.addAttribute("error", true);
        }

        emailService.saveEmails(attachedXSLX);
        viewUtils.getEmailStatisticsForEmailsPage(model);
        return "emails";
    }

    @GetMapping("/deleteAllEmails")
    public String deleteAllEmails(Model model){
        emailService.deleteAllEmails();
        viewUtils.getEmailStatisticsForEmailsPage(model);
        return "redirect:/emails";
    }

    @GetMapping("/resetSentStatus")
    public String resetIsSentStatus(Model model){
        emailService.resetSentStatus();
        viewUtils.getEmailStatisticsForEmailsPage(model);
        return "emails";
    }
}
