package com.lukushin.message_service.entity;

import com.lukushin.message_service.dto.FileDTO;
import com.lukushin.message_service.dto.SubjectDTO;
import com.lukushin.message_service.dto.TextDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "text")
    private String text;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;

    public Message(SubjectDTO subject, TextDTO text) {
        this.subject = subject.getSubject();
        this.text = text.getText();
    }

    public Message(SubjectDTO subject, TextDTO text, FileDTO file) {
        this.subject = subject.getSubject();
        this.text = text.getText();
        this.fileName = file.getFileName();
        this.filePath = file.getFilePath();
    }
}
