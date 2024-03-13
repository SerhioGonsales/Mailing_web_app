package com.lukushin.mailing_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class MessageDTO {
    private String subject;
    private String text;
    private String fileName;
    private String filePath;
}
