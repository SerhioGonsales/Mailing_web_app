package com.lukushin.content_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "texts")
public class Text {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content")
    private String text;
    @Column(name = "file_name")
    private String fileName;

    public Text(String text, String fileName) {
        this.text = text;
        this.fileName = fileName;
    }

    // конструктор для тестов
    public Text(Long id, String text, String fileName) {
        this.id = id;
        this.text = text;
        this.fileName = fileName;
    }
}

