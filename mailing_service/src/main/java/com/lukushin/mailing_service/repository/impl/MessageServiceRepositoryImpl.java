package com.lukushin.mailing_service.repository.impl;

import com.lukushin.mailing_service.dto.MessageDTO;
import com.lukushin.mailing_service.exception.NoContentException;
import com.lukushin.mailing_service.repository.MessageServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Slf4j
@Repository
public class MessageServiceRepositoryImpl implements MessageServiceRepository {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${baseUrl.messageService}")
    private String baseUrl;

    @Override
    public List<MessageDTO> findAll() {
        ResponseEntity<List<MessageDTO>> response =
                restTemplate.exchange(
                        baseUrl,
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
    public MessageDTO findAny() {
       if(findAll().isEmpty()) {
           throw new NoContentException("There is no `messages` in database " +
                "for send test message");
       }
       return findAll().get(0);
    }

}
