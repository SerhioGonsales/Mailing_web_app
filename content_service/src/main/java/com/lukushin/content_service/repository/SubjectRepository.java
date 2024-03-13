package com.lukushin.content_service.repository;

import com.lukushin.content_service.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
