package com.lukushin.message_service.repository.impl;

import com.lukushin.message_service.dto.FileDTO;
import com.lukushin.message_service.dto.SubjectDTO;
import com.lukushin.message_service.dto.TextDTO;
import com.lukushin.message_service.exception.NoContentException;
import com.lukushin.message_service.repository.ContentServiceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Repository
public class ContentServiceRepositoryImpl implements ContentServiceRepository {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${baseUrl.contentService}")
    private String baseUrl;

    @Override
    public List<SubjectDTO> findAllSubjects() {
        ResponseEntity<List<SubjectDTO>> response =
                restTemplate.exchange(
                        baseUrl + "/subjects",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );
        if (response.getStatusCode().value() == 200) {
            return response.getBody();
        }
        return Collections.emptyList();
    }

    @Override
    public List<TextDTO> findAllTexts() {
        ResponseEntity<List<TextDTO>> response =
                restTemplate.exchange(
                        baseUrl + "/texts",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );
        if (response.getStatusCode().value() == 200) {
            return response.getBody();
        }
        return Collections.emptyList();
    }

    @Override
    public List<FileDTO> findAllFiles() throws NoContentException {
        ResponseEntity<List<FileDTO>> response =
                restTemplate.exchange(
                        baseUrl + "/files",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );
        if (response.getStatusCode().value() == 200) {
            return response.getBody();
        }
        return Collections.emptyList();
    }
}
