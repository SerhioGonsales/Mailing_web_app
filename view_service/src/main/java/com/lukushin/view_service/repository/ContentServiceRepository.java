package com.lukushin.view_service.repository;

import com.lukushin.view_service.dto.AttachedFileDTO;
import com.lukushin.view_service.dto.SubjectDTO;
import com.lukushin.view_service.dto.TextDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContentServiceRepository {

    List<SubjectDTO> findAllSubjects();

    List<TextDTO> findAllTexts();

    List<AttachedFileDTO> findAllAttachedFiles();

    void addSubject(String subject);

    void addText(MultipartFile file);

    void addAttachedFile(MultipartFile file);

    void deleteAllSubjects();

    void deleteAllTexts();

    void deleteAllAttachedFiles();
}
