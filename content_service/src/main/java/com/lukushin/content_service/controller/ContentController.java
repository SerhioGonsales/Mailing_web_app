package com.lukushin.content_service.controller;

import com.lukushin.content_service.entity.AttachedFile;
import com.lukushin.content_service.entity.Subject;
import com.lukushin.content_service.entity.Text;
import com.lukushin.content_service.repository.AttachedFileRepository;
import com.lukushin.content_service.repository.SubjectRepository;
import com.lukushin.content_service.repository.TextRepository;
import com.lukushin.content_service.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/content")
public class ContentController {

    private final SubjectRepository subjectRepository;
    private final TextRepository textRepository;
    private final AttachedFileRepository attachedFileRepository;
    private final FileUtils fileUtils;

    @Autowired
    public ContentController(SubjectRepository subjectRepository,
                             TextRepository textRepository,
                             AttachedFileRepository attachedFileRepository,
                             FileUtils fileUtils) {
        this.subjectRepository = subjectRepository;
        this.textRepository = textRepository;
        this.attachedFileRepository = attachedFileRepository;
        this.fileUtils = fileUtils;
    }

    @GetMapping("/subjects")
    public ResponseEntity<List<Subject>> getAllSubjects() {
        var list = subjectRepository.findAll();
        if(list.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/texts")
    public ResponseEntity<List<Text>> getAllHTMLFilesName() {
        var list = textRepository.findAll();
        if(list.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<AttachedFile>> getAllAttachedFilesName() {
        var list = attachedFileRepository.findAll();
        if(list.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @GetMapping("/subjects/{id}")
    public ResponseEntity<Subject> findSubjectById(@PathVariable ("id") Long id) {
        return ResponseEntity.of(subjectRepository.findById(id));
    }

    @GetMapping("/texts/{id}")
    public ResponseEntity<Text> findTextById(@PathVariable ("id") Long id) {
        return ResponseEntity.of(textRepository.findById(id));
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<AttachedFile> findAttachedById(@PathVariable ("id") Long id) {
        return ResponseEntity.of(attachedFileRepository.findById(id));
    }

    @PostMapping("/subjects")
    public ResponseEntity<?> createNewSubject(
            @RequestBody Subject subject,
            UriComponentsBuilder uriComponentsBuilder) {
        if(subject == null || subject.getSubject().isBlank()){
            return ResponseEntity
                    .badRequest()
                    .body("The 'Subject' field cannot be empty");
        }
        var newSubject = subjectRepository.save(subject);
        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("content/subjects/{id}")
                        .build(Map.of("id", newSubject.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(newSubject);
    }

    @PostMapping("/texts")
    public ResponseEntity<?> createNewText(
            @RequestParam ("fileHTML") MultipartFile fileHTML,
            UriComponentsBuilder uriComponentsBuilder) {
        if(fileHTML == null || fileHTML.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("File needs to be uploaded");
        }
        var text = fileUtils.convertHTMLtoTextEntity(fileHTML);
        if (text != null) {
            var saveText = textRepository.save(text);
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .path("content/texts/{id}")
                            .build(Map.of("id", saveText.getId())))
                    .body(saveText);
        }
        return ResponseEntity
                .badRequest()
                .body("File needs to be uploaded");
    }

    @PostMapping("/files")
    public ResponseEntity<?> createNewFile(
            @RequestParam ("file") MultipartFile file,
            UriComponentsBuilder uriComponentsBuilder) {
        if (file == null || file.isEmpty()){
            return ResponseEntity
                    .badRequest()
                    .body("File needs to be uploaded");
        }
        var attachedFile = fileUtils.saveAttachedFile(file);
        var newAttachedFile = attachedFileRepository.save(attachedFile);
        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("content/files/{id}")
                        .build(Map.of("id", newAttachedFile.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(newAttachedFile);
    }

    @DeleteMapping("/subjects")
    public ResponseEntity<String> deleteAllSubjects() {
        subjectRepository.deleteAll();
        return ResponseEntity.ok("All subjects have been removed from the database");
    }

    @DeleteMapping("/texts")
    public ResponseEntity<String> deleteAllTexts() {
        textRepository.deleteAll();
        return ResponseEntity.ok("All texts have been removed from the database");
    }

    @DeleteMapping("/files")
    public ResponseEntity<String> deleteAllFiles() {
        boolean isDelete = fileUtils.deleteAllAttachedFiles();
        if(isDelete) {
            return ResponseEntity.ok("All files have been removed from the database");
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/subjects/{id}")
    public ResponseEntity<String> deleteSubjectById(@PathVariable ("id") Long id) {
        var optional = subjectRepository.findById(id);

        if(optional.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("There is no such subject in the database");
        }

        Subject subject = optional.get();
        subjectRepository.delete(subject);

        return ResponseEntity.ok("Subject \"" + subject.getSubject() + "\" has been deleted");
    }

    @DeleteMapping("/texts/{id}")
    public ResponseEntity<String> deleteTextById(@PathVariable ("id") Long id) {
        var optional = textRepository.findById(id);

        if(optional.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("There is no such text in the database");
        }

        var text = optional.get();
        textRepository.delete(text);

        return ResponseEntity.ok("Text \"" + text.getFileName() + "\" has been deleted");
    }

    @DeleteMapping("/files/{id}")
    public ResponseEntity<String> deleteAttachedFileById(@PathVariable ("id") Long id) {
        var optional = attachedFileRepository.findById(id);

        if(optional.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("There is no such file in the database");
        }

        var file = optional.get();
        fileUtils.deleteAttachedFile(file);
        attachedFileRepository.delete(file);

        return ResponseEntity.ok("File \"" + file.getFileName() + "\" has been deleted");
    }

}
