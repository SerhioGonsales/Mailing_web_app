package com.lukushin.message_service.service;

import com.lukushin.message_service.dto.FileDTO;
import com.lukushin.message_service.dto.SubjectDTO;
import com.lukushin.message_service.dto.TextDTO;
import com.lukushin.message_service.entity.Message;
import com.lukushin.message_service.exception.NoContentException;
import com.lukushin.message_service.repository.ContentServiceRepository;
import com.lukushin.message_service.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class MessageService {

    private final ContentServiceRepository contentServiceRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(ContentServiceRepository contentServiceRepository,
                          MessageRepository messageRepository) {
        this.contentServiceRepository = contentServiceRepository;
        this.messageRepository = messageRepository;
    }

    public List<Message> findAll() {
        log.info("Retrieving all messages");
        messageRepository.deleteAll();
        List<SubjectDTO> allSubjects = contentServiceRepository.findAllSubjects();
        List<TextDTO> allTexts = contentServiceRepository.findAllTexts();
        List<FileDTO> allFiles = contentServiceRepository.findAllFiles();

        if(!allSubjects.isEmpty() || !allTexts.isEmpty()) {
            for (SubjectDTO subject : allSubjects)  {
                for (TextDTO text : allTexts) {
                    if (allFiles.isEmpty()) {
                        messageRepository.save(new Message(subject, text));
                    } else {
                        for (FileDTO file : allFiles) {
                            messageRepository.save(new Message(subject, text, file));
                        }
                    }
                }
            }
            return messageRepository.findAll();
        }

        log.error("There is no content for `subjects` or `texts`");
        return Collections.emptyList();
    }

}
