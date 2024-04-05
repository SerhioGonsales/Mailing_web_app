package com.lukushin.content_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukushin.content_service.entity.AttachedFile;
import com.lukushin.content_service.entity.Subject;
import com.lukushin.content_service.entity.Text;
import com.lukushin.content_service.repository.AttachedFileRepository;
import com.lukushin.content_service.repository.SubjectRepository;
import com.lukushin.content_service.repository.TextRepository;
import com.lukushin.content_service.utils.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ExtendWith(MockitoExtension.class)
class ContentControllerTest {

    @Mock
    private SubjectRepository subjectRepository;
    @Mock
    private TextRepository textRepository;
    @Mock
    AttachedFileRepository attachedFileRepository;
    @Mock
    private FileUtils fileUtils;

    @InjectMocks
    private ContentController contentController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(contentController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllSubjects_NoEmptyList_ResponseOK() throws Exception {
        // given
        Subject subject = new Subject(1L, "new subject");
        List<Subject> subjects = Collections.singletonList(subject);
        when(subjectRepository.findAll()).thenReturn(subjects);

        // when
        mockMvc.perform(get("/content/subjects"))
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$", hasSize(1)),
                        jsonPath("$[0].id", is(1)),
                        jsonPath("$[0].subject", is("new subject"))
                );
    }

    @Test
    void getAllSubjects_EmptyList_ResponseNoContent() throws Exception {
        // given
        List<Subject> emptyList = Collections.emptyList();
        when(subjectRepository.findAll()).thenReturn(emptyList);

        // when
        mockMvc.perform(get("/content/subjects"))
                // then
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllTexts_NoEmptyList_ResponseOK() throws Exception {
        // given
        Text text = new Text(1L, "new text", "fileName.txt");
        List<Text> texts = Collections.singletonList(text);
        when(textRepository.findAll()).thenReturn(texts);

        // when
        mockMvc.perform(get("/content/texts"))
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$", hasSize(1)),
                        jsonPath("$[0].id", is(1)),
                        jsonPath("$[0].text", is("new text")),
                        jsonPath("$[0].fileName", is("fileName.txt"))
                );
    }

    @Test
    void getAllTexts_EmptyList_ResponseNoContent() throws Exception {
        // given
        List<Text> emptyList = Collections.emptyList();
        when(textRepository.findAll()).thenReturn(emptyList);

        // when
        mockMvc.perform(get("/content/texts"))
                // then
                .andExpect(status().isNoContent());
    }

    @Test
    void getAllAttachedFile_NoEmptyList_ResponseOK() throws Exception {
        // given
        AttachedFile file = new AttachedFile(1L, "C:/fileName.txt", "fileName.txt");
        List<AttachedFile> files = Collections.singletonList(file);
        when(attachedFileRepository.findAll()).thenReturn(files);

        // when
        mockMvc.perform(get("/content/files"))
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$", hasSize(1)),
                        jsonPath("$[0].id", is(1)),
                        jsonPath("$[0].filePath", is("C:/fileName.txt")),
                        jsonPath("$[0].fileName", is("fileName.txt"))
                );
    }

    @Test
    void getAllAttachedFiles_EmptyList_ResponseNoContent() throws Exception {
        // given
        List<AttachedFile> emptyList = Collections.emptyList();
        when(attachedFileRepository.findAll()).thenReturn(emptyList);

        // when
        mockMvc.perform(get("/content/files"))
                // then
                .andExpect(status().isNoContent());
    }

    @Test
    void getSubjectById_ValidId_ResponseOk() throws Exception {
        // given
        Subject subject = new Subject(1L, "new subject");
        when(subjectRepository.findById(1L)).thenReturn(Optional.of(subject));

        // when
        mockMvc.perform(get("/content/subjects/{id}", 1L))
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id", is(1)),
                        jsonPath("$.subject", is("new subject"))
                );
    }

    @Test
    void getSubjectById_InvalidId_ResponseNotFound() throws Exception {
        // given
        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        mockMvc.perform(get("/content/subjects/{id}", 1L))
                // then
                .andExpect(status().isNotFound());
    }

    @Test
    void getTextById_ValidId_ResponseOk() throws Exception {
        // given
        Text text = new Text(1L, "new text", "fileName.txt");
        when(textRepository.findById(1L)).thenReturn(Optional.of(text));

        // when
        mockMvc.perform(get("/content/texts/{id}", 1L))
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id", is(1)),
                        jsonPath("$.text", is("new text")),
                        jsonPath("$.fileName", is("fileName.txt"))
                );
    }

    @Test
    void getTextById_InvalidId_ResponseNotFound() throws Exception {
        // given
        when(textRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        mockMvc.perform(get("/content/texts/{id}", 1L))
                // then
                .andExpect(status().isNotFound());
    }

    @Test
    void getAttachedFileById_ValidId_ResponseOk() throws Exception {
        // given
        AttachedFile file = new AttachedFile(1L, "C:/fileName.txt", "fileName.txt");
        when(attachedFileRepository.findById(1L)).thenReturn(Optional.of(file));

        // when
        mockMvc.perform(get("/content/files/{id}", 1L))
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id", is(1)),
                        jsonPath("$.filePath", is("C:/fileName.txt")),
                        jsonPath("$.fileName", is("fileName.txt"))
                );
    }

    @Test
    void getAttachedFileById_InvalidId_ResponseNotFound() throws Exception {
        // given
        when(attachedFileRepository.findById(1L)).thenReturn(Optional.empty());

        // when
        mockMvc.perform(get("/content/files/{id}", 1L))
                // then
                .andExpect(status().isNotFound());
    }

    @Test
    void createNewSubject_ValidSubject_ResponseCreated() throws Exception {
        // given
        Subject subject = new Subject(1L, "new subject");
        String subjectJSON = objectMapper.writeValueAsString(subject);
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject);

        // when
        mockMvc.perform(post("/content/subjects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(subjectJSON))
                // then
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.subject", is("new subject")),
                        header().string("Location", containsString("/content/subjects/1"))
                );
        verify(subjectRepository, times(1)).save(any(Subject.class));
    }

    @Test
    void createNewSubject_EmptySubject_ResponseBedRequest() throws Exception {
        // given
        String emptySubject = objectMapper.writeValueAsString(new Subject(1L, "   "));

        // when
        mockMvc.perform(post("/content/subjects")
                        .content(emptySubject)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The 'Subject' field cannot be empty"));
        verify(subjectRepository, never()).save(any());
    }

    @Test
    void createNewText_withValidHTMLFile_ResponseCreated() throws Exception {
        // given
        MockMultipartFile file =
                new MockMultipartFile(
                        "fileHTML",
                        "fileName.html",
                        "text/html",
                        "Hello, World!".getBytes()
                );

        var text = new Text(1L, "text", "fileName.html");

        when(fileUtils.convertHTMLtoTextEntity(file)).thenReturn(text);
        when(textRepository.save(any(Text.class))).thenReturn(text);

        var textJSON = objectMapper.writeValueAsString(text);

        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/content/texts")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                // then
                .andExpectAll(
                        status().isCreated(),
                        header().string("Location", containsString("/content/texts/1")),
                        content().json(textJSON)
                );
    }

    @Test
    void createNewText_withNoHTMLFile_BadRequest() throws Exception {
        // given
        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "test.txt",
                        "text/plain",
                        (byte[]) null
                );

        // when
        mockMvc.perform(multipart("/content/texts")
                        .file(file))
                // then
                .andExpect(status().isBadRequest());
    }

    @Test
    void createNewFile_withValidFile_shouldReturnCreated() throws Exception {
        // given
        byte[] fileContent = "Hello, world!".getBytes();
        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "testfile.txt",
                        "application/octet-stream",
                        fileContent
                );

        var attachedFile = new AttachedFile("C:/fileName.txt", "fileName.txt");
        attachedFile.setId(1L);
        when(fileUtils.saveAttachedFile(any(MultipartFile.class))).thenReturn(attachedFile);
        when(attachedFileRepository.save(any(AttachedFile.class))).thenReturn(attachedFile);

        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/content/files")
                        .file(file))
                // then
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        header().string("Location", containsString("/content/files/1")),
                        jsonPath("$.id", is(1)),
                        jsonPath("$.filePath", is("C:/fileName.txt")),
                        jsonPath("$.fileName", is("fileName.txt"))
                );
    }

    @Test
    void createNewFile_withNoFile_shouldReturnBadRequest() throws Exception {
        // given
        MockMultipartFile emptyFile =
                new MockMultipartFile(
                        "file",
                        "test.txt",
                        "application/octet-stream",
                        (byte[]) null
                );

        // when
        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/content/files")
                        .file(emptyFile))
                // then
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("File needs to be uploaded")
                );
    }

    @Test
    void deleteAllSubjects_ResponseOK() throws Exception {
        // when
        mockMvc.perform(delete("/content/subjects"))
                // then
                .andExpectAll(
                        status().isOk(),
                        content().string("All subjects have been removed from the database")
                );
        verify(subjectRepository, times(1)).deleteAll();
    }

    @Test
    void deleteAllTexts_ResponseOK() throws Exception {
        // when
        mockMvc.perform(delete("/content/texts"))
                // then
                .andExpectAll(
                        status().isOk(),
                        content().string("All texts have been removed from the database")
                );
        verify(textRepository, times(1)).deleteAll();
    }

    @ParameterizedTest
    @CsvSource({"200, All files have been removed from the database, true",
            "204, '', false"})
    void deleteAllFiles_ResponseOk(int httpStatus, String expectedContent, boolean isDelete) throws Exception {
        // given
        when(fileUtils.deleteAllAttachedFiles()).thenReturn(isDelete);

        // when
        mockMvc.perform(delete("/content/files"))
                // then
                .andExpectAll(
                        status().is(httpStatus),
                        content().string(expectedContent)
                );
    }

    @Test
    void deleteSubjectById_ResponseOK() throws Exception {
        // given
        Long id = 1L;
        Subject subject = new Subject(id, "test subject");
        when(subjectRepository.findById(id)).thenReturn(Optional.of(subject));

        // when
        mockMvc.perform(delete("/content/subjects/{id}", id))
                // then
                .andExpectAll(
                        status().isOk(),
                        content().string("Subject \"test subject\" has been deleted")
                );
    }

    @Test
    void deleteSubjectById_NotFound() throws Exception {
        // given
        Long id = 2L;
        when(subjectRepository.findById(id)).thenReturn(Optional.empty());

        // when
        mockMvc.perform(delete("/content/subjects/{id}", id))
                // then
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("There is no such subject in the database")
                );
        verify(subjectRepository, never()).delete(any());
    }

    @Test
    void deleteTextById_ResponseOK() throws Exception {
        // given
        Long id = 1L;
        var text = new Text("text", "fileName.html");
        when(textRepository.findById(id)).thenReturn(Optional.of(text));

        // when
        mockMvc.perform(delete("/content/texts/{id}", id))
                // then
                .andExpectAll(
                        status().isOk(),
                        content().string("Text \"" + "fileName.html" + "\" has been deleted")
                );
        verify(textRepository, times(1)).delete(text);
    }

    @Test
    void deleteTextById_BadRequest() throws Exception {
        // given
        Long id = 2L;
        when(textRepository.findById(id)).thenReturn(Optional.empty());

        // when
        mockMvc.perform(delete("/content/texts/{id}", id))
                // then
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("There is no such text in the database")
                );
        verify(textRepository, never()).delete(any());
    }

    @Test
    void deleteAttachedFileById_ResponseOK() throws Exception {
        // given
        Long id = 1L;
        var file = new AttachedFile("C:/fileName.txt", "fileName.txt");
        when(attachedFileRepository.findById(id)).thenReturn(Optional.of(file));

        // when
        mockMvc.perform(delete("/content/files/{id}", id))
                // then
                .andExpectAll(
                        status().isOk(),
                        content().string("File \"fileName.txt\" has been deleted")
                );
        verify(attachedFileRepository, times(1)).delete(file);
    }

    @Test
    void deleteAttachedFileById_BadRequest() throws Exception {
        // given
        Long id = 2L;

        when(attachedFileRepository.findById(id)).thenReturn(Optional.empty());

        // when
        mockMvc.perform(delete("/content/files/{id}", id))
                // then
                .andExpectAll(
                        status().isBadRequest(),
                        content().string("There is no such file in the database")
                );
        verify(attachedFileRepository, never()).delete(any());
    }
}

