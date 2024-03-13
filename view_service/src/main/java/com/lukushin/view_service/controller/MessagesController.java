package com.lukushin.view_service.controller;

import com.lukushin.view_service.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;

@Controller
public class MessagesController {

    private final ContentService contentService;

    @Autowired
    public MessagesController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/messages")
    public String messages(Model model){
        model.addAttribute("subjects", contentService.findAllSubjects());
        model.addAttribute("texts", contentService.findAllTexts());
        model.addAttribute("files", contentService.findAllAttachedFiles());
        model.addAttribute("emptyAttachedHTML", false);
        return "messages";
    }

    @PostMapping("/addSubject")
    private String addSubject(@RequestParam ("subject") String subject){
        if (!subject.isBlank()){
            contentService.addSubject(subject);
        }
        return "redirect:/messages";
    }

    @PostMapping("/addText")
    public String saveText(
            @RequestParam ("attachedHTML") MultipartFile attachedHTML){
        contentService.addText(attachedHTML);
        return "redirect:/messages";
    }

    @PostMapping("/addFile")
    private String addFile(@RequestParam ("attachedFile") MultipartFile attachedFile){
        contentService.addAttachedFile(attachedFile);
        return "redirect:/messages";
    }

    @GetMapping("/deleteAllSubjects")
    private String deleteAllSubjects(){
        contentService.deleteAllSubjects();
        return "redirect:/messages";
    }

    @GetMapping("/deleteAllTexts")
    private String deleteAllTexts(){
        contentService.deleteAllTexts();
        return "redirect:/messages";
    }

    @GetMapping("/deleteAllFiles")
    private String deleteAllFiles(){
        contentService.deleteAllAttachedFiles();
        return "redirect:/messages";
    }
}
