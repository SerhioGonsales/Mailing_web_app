package com.lukushin.view_service.repository;

import com.lukushin.view_service.dto.EmailDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmailServiceRepository {
    List<EmailDTO> findAll();

    void saveEmails(MultipartFile file);

    void deleteAll();

    void resetSentStatus();
}
