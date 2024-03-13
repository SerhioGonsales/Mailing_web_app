package com.lukushin.mailing_service.repository;

import com.lukushin.mailing_service.dto.MessageDTO;

import java.util.List;

public interface MessageServiceRepository {
    List<MessageDTO> findAll();
    MessageDTO findAny();
}
