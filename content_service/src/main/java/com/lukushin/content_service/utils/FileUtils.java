package com.lukushin.content_service.utils;

import com.lukushin.content_service.entity.AttachedFile;
import com.lukushin.content_service.entity.Text;
import com.lukushin.content_service.exception.FileException;
import com.lukushin.content_service.exception.NoContentException;
import com.lukushin.content_service.repository.AttachedFileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class FileUtils {

    @Value("${file.upload-dir}")
    String uploadPath;

    private final AttachedFileRepository attachedFileRepository;

    @Autowired
    public FileUtils(AttachedFileRepository attachedFileRepository) {
        this.attachedFileRepository = attachedFileRepository;
    }

    // метод парсит HTML файл и возвращает объект Text
    public Text convertHTMLtoTextEntity(MultipartFile file) {
        log.info("Start parsing HTML file");
        String parseHTML;

        if(file.isEmpty()){
            throw new FileException("File not loaded");
        }

        try {
            byte[] fileBytes = file.getBytes();
            parseHTML = new String(fileBytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new FileException("An error occurred while working with the file.");
        }

        String HTMLFileName = file.getOriginalFilename();
        log.info("Parsing complete");
        return new Text(parseHTML, HTMLFileName);
    }

    // метод сохраняет файл (коммерческое, прайс и т.п.) в локальную папку
    // и возвращает объект AttachedFile (для дальнейшего сохранения через репозиторий)
    public AttachedFile saveAttachedFile(MultipartFile file) {
        log.info("Start parsing attached file");
        String filePath;

        if(file.isEmpty()){
            throw new FileException("File not loaded");
        }

        File uploadFolder = new File(uploadPath);

        if(!uploadFolder.exists()){
            uploadFolder.mkdir();
        }

        String fileName = file.getOriginalFilename();
        File dest = new File(uploadFolder,fileName);
        filePath = dest.getAbsolutePath();

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new FileException("An error occurred while working with the file.");
        }
        log.info("File {} successfully saved", fileName);
        return new AttachedFile(filePath, fileName);
    }

    // удаляет все записи в базе и все файлы в локальной папке
    public boolean deleteAllAttachedFiles() {
        log.info("Deleting all attached files");
        attachedFileRepository.deleteAll();
        File folder = new File(uploadPath);
        File[] files = folder.listFiles();

        if(files == null || files.length == 0){
            throw new NoContentException("No files to delete");
        }

        for (File file : files){
            file.delete();
        }

        log.info("All files deleted");
        return true;
    }

    // удаляет конкретный файл из локальной папки
    public void deleteAttachedFile(AttachedFile attachedFile) {
        log.info("Deleting file {}", attachedFile.getFileName());
        new File(attachedFile.getFilePath()).delete();
        log.info("File {} successfully deleted", attachedFile.getFileName());
    }
}
