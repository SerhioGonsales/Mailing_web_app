package com.lukushin.mailing_service.repository;

import com.lukushin.mailing_service.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailServiceRepository extends JpaRepository<Email, Long> {
    List<Email> findByIsEmailSentFalse();
}
