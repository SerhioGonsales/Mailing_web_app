package com.lukushin.email_service.repository;

import com.lukushin.email_service.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Email e SET e.isEmailSent = false")
    void resetSentStatus();

    boolean existsByEmail(String email);

    Optional<Email> findByEmail(String email);
}
