package com.lukushin.view_service.repository.Impl;

import com.lukushin.view_service.dto.AttachedFileDTO;
import com.lukushin.view_service.dto.SubjectDTO;
import com.lukushin.view_service.dto.TextDTO;
import com.lukushin.view_service.repository.ContentServiceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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
        if(response.getStatusCode().value() == 200) {
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
        if(response.getStatusCode().value() == 200) {
            return response.getBody();
        }
        return Collections.emptyList();
    }

    @Override
    public List<AttachedFileDTO> findAllAttachedFiles() {
        ResponseEntity<List<AttachedFileDTO>> response =
                restTemplate.exchange(
                        baseUrl + "/files",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );
        if(response.getStatusCode().value() == 200) {
            return response.getBody();
        }
        return Collections.emptyList();
    }

    @Override
    public void addSubject(String subject) {
        SubjectDTO newSubject = new SubjectDTO(subject);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SubjectDTO> request = new HttpEntity<>(newSubject, headers);

        restTemplate.exchange(
                baseUrl + "/subjects",
                HttpMethod.POST,
                request,
                String.class
        );

//        System.out.println("Response status: " + response.getStatusCode());
//        System.out.println("Location: " + response.getHeaders().getLocation());
//        System.out.println("Body: " + response.getBody());
    }

    @Override
    public void addText(MultipartFile file) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("fileHTML", file.getResource());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        restTemplate.exchange(
                baseUrl + "/texts",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<String>() {}
        );
    }

    @Override
    public void addAttachedFile(MultipartFile file) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", file.getResource());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        restTemplate.exchange(
                baseUrl + "/files",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<String>() {}
        );
    }

    @Override
    public void deleteAllSubjects() {
        restTemplate.exchange(
                baseUrl + "/subjects",
                HttpMethod.DELETE,
                null,
                String.class
        );
    }

    @Override
    public void deleteAllTexts() {
        restTemplate.exchange(
                baseUrl + "/texts",
                HttpMethod.DELETE,
                null,
                String.class
        );
    }

    @Override
    public void deleteAllAttachedFiles() {
        restTemplate.exchange(
                baseUrl + "/files",
                HttpMethod.DELETE,
                null,
                String.class
        );
    }
}
