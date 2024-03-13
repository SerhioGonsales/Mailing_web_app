package com.lukushin.view_service.repository.Impl;

import com.lukushin.view_service.repository.MailingServiceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MailingServiceRepositoryImpl implements MailingServiceRepository {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${baseUrl.mailingService}")
    private String baseUrl;

    @Override
    public boolean startMailing() {
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                String.class
        );
        return response.getStatusCode().value() == 200;
    }

    @Override
    public boolean sendTestMessage() {
        ResponseEntity<String> response = restTemplate.exchange(
                baseUrl + "/test",
                HttpMethod.GET,
                null,
                String.class
        );
        return response.getStatusCode().value() == 200;
    }
}
