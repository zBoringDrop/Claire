package it.unict.davidemilazzo.claire.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unict.davidemilazzo.claire.config.JwtUtil;
import it.unict.davidemilazzo.claire.dto.FileDto;
import it.unict.davidemilazzo.claire.exception.ExceptionMessages;
import it.unict.davidemilazzo.claire.exception.FileEmptyException;
import it.unict.davidemilazzo.claire.exception.IdNotFoundException;
import it.unict.davidemilazzo.claire.exception.NotTheOwnerException;
import it.unict.davidemilazzo.claire.model.*;
import it.unict.davidemilazzo.claire.respository.FileEntityRepository;
import it.unict.davidemilazzo.claire.respository.ProgrammingLanguageRepository;
import it.unict.davidemilazzo.claire.respository.UserEntityRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileCodeControllerTest {
    private final String BASE_API_URL = "/api/file";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private FileEntityRepository fileEntityRepository;

    @Autowired
    private ProgrammingLanguageRepository programmingLanguageRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String mainUserJwtToken;
    private UserEntity mainUser;
    private UserEntity secondaryUser;
    private ProgrammingLanguageEntity programmingLanguage;
    private ProgrammingLanguageEntity programmingLanguageDefault;

    @BeforeAll
    void init() {
        fileEntityRepository.deleteAll();
        programmingLanguageRepository.deleteAll();
        userEntityRepository.deleteAll();

        programmingLanguage = new ProgrammingLanguageEntity();
        programmingLanguage.setName("JavaTest");
        programmingLanguage = programmingLanguageRepository.saveAndFlush(programmingLanguage);

        programmingLanguageDefault = new ProgrammingLanguageEntity();
        programmingLanguageDefault.setName("PSEUDO_CODE");
        programmingLanguageDefault = programmingLanguageRepository.saveAndFlush(programmingLanguageDefault);

        UserEntity user1 = new UserEntity();
        user1.setNickname("User1");
        user1.setName("UserName1");
        user1.setSurname("UserSurname1");
        user1.setEmail("user1@email.com");
        user1.setPassword(passwordEncoder.encode("password1"));
        user1.setRole(Role.USER);
        mainUser = userEntityRepository.save(user1);

        mainUserJwtToken = jwtUtil.generateToken(user1.getEmail());

        UserEntity user2 = new UserEntity();
        user2.setNickname("User2");
        user2.setName("UserName2");
        user2.setSurname("UserSurname2");
        user2.setEmail("user2@email.com");
        user2.setPassword(passwordEncoder.encode("password2"));
        user2.setRole(Role.USER);
        secondaryUser = userEntityRepository.save(user2);
    }

    @BeforeEach
    void resetDatabase() {
        fileEntityRepository.deleteAll();
    }

    @Test
    @DisplayName("TEST file upload success")
    void fileUpload_success() throws Exception {
        String filename = "uploadedFile";
        String originalFilename = "myCode.JavaTest";
        String contentType = "text/plain";
        String contentData = "class Test {}";
        MockMultipartFile mockFile = new MockMultipartFile(
                filename,
                originalFilename,
                contentType,
                contentData.getBytes()
        );

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart(BASE_API_URL + "/upload")
                        .file(mockFile)
                        .header("Authorization", "Bearer " + mainUserJwtToken))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        FileDto fileDto = objectMapper.readValue(jsonResponse, FileDto.class);

        assertEquals(1, fileEntityRepository.count());
        assertTrue(fileEntityRepository.existsById(fileDto.getId()));

        assertEquals(originalFilename, fileDto.getFilename());
        assertEquals(mockFile.getSize(), fileDto.getSize());
        assertEquals(contentData, fileDto.getData());
        assertTrue(Duration.between(fileDto.getUploadDatetime(), LocalDateTime.now()).abs().toSeconds() < 2);
    }

    @Test
    @DisplayName("TEST file upload failed cause of empty file")
    void fileUpload_failedCauseOfEmptyFile() throws Exception {
        String filename = "uploadedFile";
        String originalFilename = "myCode.JavaTest";
        String contentType = "text/plain";
        String contentData = "";
        MockMultipartFile mockFile = new MockMultipartFile(
                filename,
                originalFilename,
                contentType,
                contentData.getBytes()
        );

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart(BASE_API_URL + "/upload")
                        .file(mockFile)
                        .header("Authorization", "Bearer " + mainUserJwtToken))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

        Exception resolvedException = result.getResolvedException();

        Assertions.assertNotNull(resolvedException);
        Assertions.assertInstanceOf(FileEmptyException.class, resolvedException);
        Assertions.assertEquals(ExceptionMessages.FILE_IS_EMPTY, resolvedException.getMessage());

        assertEquals(0, fileEntityRepository.count());
        assertFalse(fileEntityRepository.existsById(1L));
    }

    @Test
    @DisplayName("TEST file delete success")
    void fileDelete_success() throws Exception {
        String filename = "myCode.JavaTest";
        String originalFilename = "myCode.JavaTest";
        String contentType = "text/plain";
        String contentData = "class Test {}";

        FileEntity fileEntity = new FileEntity();
        fileEntity.setUserEntity(mainUser);
        fileEntity.setFilename(originalFilename);
        fileEntity.setContentType(contentType);
        fileEntity.setLanguageEntity(programmingLanguage);
        fileEntity.setSize(500L);
        fileEntity.setUploadDatetime(LocalDateTime.now());
        fileEntity.setData(contentData);
        Long fileId = fileEntityRepository.save(fileEntity).getId();

        assertEquals(1, fileEntityRepository.count());

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_API_URL + "/delete/" + fileId)
                        .header("Authorization", "Bearer " + mainUserJwtToken))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(0, fileEntityRepository.count());
        assertFalse(fileEntityRepository.existsById(fileId));
    }

    @Test
    @DisplayName("TEST file delete error file id not exists")
    void fileDelete_errorFileIdNotExists() throws Exception {
        Long id = 9999L;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(BASE_API_URL + "/delete/" + id)
                        .header("Authorization", "Bearer " + mainUserJwtToken))
                .andExpect(status().isNotFound())
                .andReturn();

        Exception resolvedException = result.getResolvedException();

        Assertions.assertNotNull(resolvedException);
        Assertions.assertInstanceOf(IdNotFoundException.class, resolvedException);
        Assertions.assertEquals(ExceptionMessages.ID_NOT_FOUND, resolvedException.getMessage());

        assertFalse(fileEntityRepository.existsById(id));
    }

    @Test
    @DisplayName("TEST file delete error different owner")
    void fileDelete_errorCauseOfDifferentOwner() throws Exception {
        String originalFilename = "myCode.JavaTest";
        String contentType = "text/plain";
        String contentData = "class Test {}";

        FileEntity fileEntity = new FileEntity();
        fileEntity.setUserEntity(secondaryUser);
        fileEntity.setFilename(originalFilename);
        fileEntity.setContentType(contentType);
        fileEntity.setLanguageEntity(programmingLanguage);
        fileEntity.setSize(500L);
        fileEntity.setUploadDatetime(LocalDateTime.now());
        fileEntity.setData(contentData);
        Long fileId = fileEntityRepository.save(fileEntity).getId();

        assertEquals(1, fileEntityRepository.count());

        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.delete(BASE_API_URL + "/delete/" + fileId)
                        .header("Authorization", "Bearer " + mainUserJwtToken))
                .andExpect(status().isUnauthorized())
                .andReturn();

        Exception resolvedException = result1.getResolvedException();

        Assertions.assertNotNull(resolvedException);
        Assertions.assertInstanceOf(NotTheOwnerException.class, resolvedException);
        Assertions.assertEquals(ExceptionMessages.NOT_THE_OWNER, resolvedException.getMessage());

        assertEquals(1, fileEntityRepository.count());
        assertTrue(fileEntityRepository.existsById(fileId));
    }

    @Test
    @DisplayName("TEST user files list success")
    void fileList_success() throws Exception {
        String originalFilename = "myCode1.JavaTest";
        String originalFilename2 = "myCode2.JavaTest";
        String contentType = "text/plain";
        String contentData = "class Test1 {}";
        String contentData2 = "class Test1 {}";

        FileEntity fileEntity = new FileEntity();
        fileEntity.setUserEntity(mainUser);
        fileEntity.setFilename(originalFilename);
        fileEntity.setContentType(contentType);
        fileEntity.setLanguageEntity(programmingLanguage);
        fileEntity.setSize(500L);
        fileEntity.setUploadDatetime(LocalDateTime.now());
        fileEntity.setData(contentData);
        fileEntityRepository.save(fileEntity);

        FileEntity fileEntity2 = new FileEntity();
        fileEntity2.setUserEntity(mainUser);
        fileEntity2.setFilename(originalFilename2);
        fileEntity2.setContentType(contentType);
        fileEntity2.setLanguageEntity(programmingLanguage);
        fileEntity2.setSize(500L);
        fileEntity2.setUploadDatetime(LocalDateTime.now());
        fileEntity2.setData(contentData2);
        fileEntityRepository.save(fileEntity2);

        assertEquals(2, fileEntityRepository.count());

        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.get(BASE_API_URL + "/list")
                        .header("Authorization", "Bearer " + mainUserJwtToken))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse1 = result1.getResponse().getContentAsString();
        List<FileDto> fileDtoList = objectMapper.readValue(
                jsonResponse1,
                new TypeReference<List<FileDto>>() {}
        );

        for (FileDto f : fileDtoList) {
            assertEquals(mainUser.getId(), f.getUserId());
        }
    }

    @Test
    @DisplayName("TEST user files get success")
    void fileList_getSuccess() throws Exception {
        String originalFilename = "myCode.JavaTest";
        String contentType = "text/plain";
        String contentData = "class Test {}";

        FileEntity fileEntity = new FileEntity();
        fileEntity.setUserEntity(mainUser);
        fileEntity.setFilename(originalFilename);
        fileEntity.setContentType(contentType);
        fileEntity.setLanguageEntity(programmingLanguage);
        fileEntity.setSize(500L);
        fileEntity.setUploadDatetime(LocalDateTime.now());
        fileEntity.setData(contentData);
        Long fileId = fileEntityRepository.save(fileEntity).getId();

        assertEquals(1, fileEntityRepository.count());

        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.get(BASE_API_URL + "/get/" + fileId)
                        .header("Authorization", "Bearer " + mainUserJwtToken))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse1 = result1.getResponse().getContentAsString();
        FileDto fileDtoRes = objectMapper.readValue(jsonResponse1, FileDto.class);

        assertEquals(fileId, fileDtoRes.getId());
        assertTrue(fileEntityRepository.existsById(fileDtoRes.getId()));
        assertEquals(originalFilename, fileDtoRes.getFilename());
        assertEquals(contentData, fileDtoRes.getData());
    }

    @Test
    @DisplayName("TEST user files get error not the owner")
    void fileList_getErrorNotTheOwner() throws Exception {
        String originalFilename = "myCode.JavaTest";
        String contentType = "text/plain";
        String contentData = "class Test {}";

        FileEntity fileEntity = new FileEntity();
        fileEntity.setUserEntity(secondaryUser);
        fileEntity.setFilename(originalFilename);
        fileEntity.setContentType(contentType);
        fileEntity.setLanguageEntity(programmingLanguage);
        fileEntity.setSize(500L);
        fileEntity.setUploadDatetime(LocalDateTime.now());
        fileEntity.setData(contentData);
        Long fileId = fileEntityRepository.save(fileEntity).getId();

        assertEquals(1, fileEntityRepository.count());

        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.get(BASE_API_URL + "/get/" + fileId)
                        .header("Authorization", "Bearer " + mainUserJwtToken))
                .andExpect(status().isUnauthorized())
                .andReturn();

        Exception resolvedException = result1.getResolvedException();

        Assertions.assertNotNull(resolvedException);
        Assertions.assertInstanceOf(NotTheOwnerException.class, resolvedException);
        Assertions.assertEquals(ExceptionMessages.NOT_THE_OWNER, resolvedException.getMessage());
    }
}
