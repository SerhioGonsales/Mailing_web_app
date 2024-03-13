package com.lukushin.message_service.repository;

import com.lukushin.message_service.dto.FileDTO;
import com.lukushin.message_service.dto.SubjectDTO;
import com.lukushin.message_service.dto.TextDTO;

import java.util.List;

public interface ContentServiceRepository {
        List<SubjectDTO> findAllSubjects();
        List<TextDTO> findAllTexts();
        List<FileDTO> findAllFiles();
}