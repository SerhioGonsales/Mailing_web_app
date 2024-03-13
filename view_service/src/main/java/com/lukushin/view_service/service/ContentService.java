package com.lukushin.view_service.service;

import com.lukushin.view_service.dto.AttachedFileDTO;
import com.lukushin.view_service.dto.SubjectDTO;
import com.lukushin.view_service.dto.TextDTO;
import com.lukushin.view_service.repository.ContentServiceRepository;
import com.lukushin.view_service.repository.Impl.ContentServiceRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ContentService {

    private final ContentServiceRepository contentServiceRepository;

    @Autowired
    public ContentService(ContentServiceRepository contentServiceRepository) {
        this.contentServiceRepository = contentServiceRepository;
    }

    public List<SubjectDTO> findAllSubjects() {
        return contentServiceRepository.findAllSubjects();
    }

    public List<TextDTO> findAllTexts() {
        return contentServiceRepository.findAllTexts();
    }

    public List<AttachedFileDTO> findAllAttachedFiles() {
        return contentServiceRepository.findAllAttachedFiles();
    }

    public void addSubject(String subject) {
        contentServiceRepository.addSubject(subject);
    }

    public void addText(MultipartFile file) {
        contentServiceRepository.addText(file);
    }

    public void addAttachedFile(MultipartFile attachedFile) {
        contentServiceRepository.addAttachedFile(attachedFile);
    }

    public void deleteAllSubjects() {
        contentServiceRepository.deleteAllSubjects();
    }

    public void deleteAllTexts() {
        contentServiceRepository.deleteAllTexts();
    }

    public void deleteAllAttachedFiles() {
        contentServiceRepository.deleteAllAttachedFiles();
    }
}
