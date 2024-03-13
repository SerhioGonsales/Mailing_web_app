package com.lukushin.content_service.repository;

import com.lukushin.content_service.entity.Text;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextRepository extends JpaRepository<Text, Long> {
}
