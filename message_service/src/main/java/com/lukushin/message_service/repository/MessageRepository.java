package com.lukushin.message_service.repository;

import com.lukushin.message_service.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
