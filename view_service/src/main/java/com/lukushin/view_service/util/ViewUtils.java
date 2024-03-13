package com.lukushin.view_service.util;

import com.lukushin.view_service.dto.EmailDTO;
import com.lukushin.view_service.repository.EmailServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.List;

@Component
public class ViewUtils {

    private final EmailServiceRepository emailServiceRepository;

    @Autowired
    public ViewUtils(EmailServiceRepository emailServiceRepository) {
        this.emailServiceRepository = emailServiceRepository;
    }

    public void getEmailStatisticsForEmailsPage(Model model) {
        List<EmailDTO> emailList = emailServiceRepository.findAll();
        int emailsInBase = emailList.size();
        int emailsIsSent = emailsIsSent(emailList);
        int emailsNotSent = emailsNotSent(emailList);
        model.addAttribute("emailsInBase", emailsInBase);
        model.addAttribute("emailsIsSent", emailsIsSent);
        model.addAttribute("emailsNotSent", emailsNotSent);
    }

    private int emailsIsSent(List<EmailDTO> emailList){
        return (int) emailList.stream()
                .filter(EmailDTO::isEmailSent)
                .count();
    }

    private int emailsNotSent(List<EmailDTO> emailList){
        return (int) emailList.stream()
                .filter(x -> !x.isEmailSent())
                .count();
    }
}
