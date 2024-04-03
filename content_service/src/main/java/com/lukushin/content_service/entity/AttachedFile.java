package com.lukushin.content_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "attached_files")
public class AttachedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "file_name")
    private String fileName;

    public AttachedFile(String filePath, String fileName){
        this.filePath = filePath;
        this.fileName = fileName;
    }

    // конструктор для тестов
    public AttachedFile(Long id, String filePath, String fileName){
        this.id = id;
        this.filePath = filePath;
        this.fileName = fileName;
    }
}
