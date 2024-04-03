package com.lukushin.content_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "subject")
    private String subject;

    public Subject(String subject) {
        this.subject = subject;
    }

    // конструктор для тестов
    public Subject(Long id, String subject) {
        this.id = id;
        this.subject = subject;
    }


}
