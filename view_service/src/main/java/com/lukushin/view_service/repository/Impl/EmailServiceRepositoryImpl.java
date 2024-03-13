package com.lukushin.view_service.repository.Impl;

import com.lukushin.view_service.dto.EmailDTO;
import com.lukushin.view_service.repository.EmailServiceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Repository
public class EmailServiceRepositoryImpl implements EmailServiceRepository {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${baseUrl.emailService}")
    private String baseUrl;

    @Override
    public List<EmailDTO> findAll() {
        ResponseEntity<List<EmailDTO>> response =
                restTemplate.exchange(
                        baseUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>() {}
                );
        return response.getBody();
    }

    @Override
    public void saveEmails(MultipartFile file) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("XLSXFile", file.getResource());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        restTemplate.exchange(
                baseUrl,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<String>() {}
        );
    }

    @Override
    public void deleteAll() {
        restTemplate.exchange(
                baseUrl,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );
    }

    @Override
    public void resetSentStatus() {
        restTemplate.exchange(
                baseUrl + "/resetSentStatus",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {
                }
        );
    }
}
