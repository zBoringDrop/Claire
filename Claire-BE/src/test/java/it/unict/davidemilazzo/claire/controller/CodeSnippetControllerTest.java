package it.unict.davidemilazzo.claire.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import it.unict.davidemilazzo.claire.config.JwtUtil;
import it.unict.davidemilazzo.claire.dto.*;
import it.unict.davidemilazzo.claire.exception.*;
import it.unict.davidemilazzo.claire.model.*;
import it.unict.davidemilazzo.claire.respository.CodeSnippetEntityRepository;
import it.unict.davidemilazzo.claire.respository.ProgrammingLanguageRepository;
import it.unict.davidemilazzo.claire.respository.UserEntityRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CodeSnippetControllerTest {

    private final String BASE_API_URL = "/api/codesnippet";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private CodeSnippetEntityRepository codeSnippetEntityRepository;

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
        codeSnippetEntityRepository.deleteAll();
        programmingLanguageRepository.deleteAll();
        userEntityRepository.deleteAll();

        programmingLanguage = new ProgrammingLanguageEntity();
        programmingLanguage.setName("JavaTest");
        programmingLanguage = programmingLanguageRepository.save(programmingLanguage);

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
        codeSnippetEntityRepository.deleteAll();
    }

    /*
    @Test
    @DisplayName("TEST code snippet upload success")
    void codeSnippetUpload_success() throws Exception {
        CodeSnippetDto codeSnippetDto = new CodeSnippetDto();
        codeSnippetDto.setProgrammingLanguageId(programmingLanguage.getId());
        codeSnippetDto.setTitle("Title.JavaTest");
        codeSnippetDto.setCodeText("void testFunc(){}");

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post(BASE_API_URL + "/upload")
                                .header("Authorization", "Bearer " + mainUserJwtToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(codeSnippetDto))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        String responseJson = result.getResponse().getContentAsString();
        CodeSnippetDto codeSnippetDtoRes = objectMapper.readValue(responseJson, CodeSnippetDto.class);

        Assertions.assertEquals(1, codeSnippetEntityRepository.count());
        Assertions.assertTrue(codeSnippetEntityRepository.existsById(codeSnippetDtoRes.getId()));

        assertNotNull(codeSnippetDtoRes.getId());
        assertTrue(codeSnippetDtoRes.getId() > 0L);
        assertEquals(mainUser.getId(), codeSnippetDtoRes.getUserId());
        assertTrue(Duration.between(codeSnippetDtoRes.getCreationDatetime(), LocalDateTime.now()).abs().toSeconds() < 2);
        //assertEquals(codeSnippetDto.getProgrammingLanguageId(), codeSnippetDtoRes.getProgrammingLanguageId());
        assertEquals(codeSnippetDto.getCodeText(), codeSnippetDtoRes.getCodeText());
    }

    @Test
    @DisplayName("TEST code snippet upload failed cause of empty file")
    void codeSnippetUpload_failedCauseOfEmptyFile() throws Exception {
        CodeSnippetDto codeSnippetDto = new CodeSnippetDto();
        codeSnippetDto.setProgrammingLanguageId(programmingLanguage.getId());
        codeSnippetDto.setTitle("Title.JavaTest");
        codeSnippetDto.setCodeText("");

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post(BASE_API_URL + "/upload")
                                .header("Authorization", "Bearer " + mainUserJwtToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(codeSnippetDto))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

        Exception resolvedException = result.getResolvedException();

        Assertions.assertNotNull(resolvedException);
        Assertions.assertInstanceOf(CodeSnippetEmptyException.class, resolvedException);
        Assertions.assertEquals(ExceptionMessages.CODE_SNIPPET_IS_EMPTY, resolvedException.getMessage());

        assertEquals(0, codeSnippetEntityRepository.count());
        assertFalse(codeSnippetEntityRepository.existsById(1L));
    }

    @Test
    @DisplayName("TEST code snippet delete success")
    void codeSnippetDelete_success() throws Exception {
        CodeSnippetEntity codeSnippetEntity = new CodeSnippetEntity();
        codeSnippetEntity.setCreationDatetime(LocalDateTime.now());
        codeSnippetEntity.setLanguageEntity(programmingLanguage);
        codeSnippetEntity.setCodeText("void testFunc(){}");
        codeSnippetEntity.setTitle("Title.JavaTest");
        codeSnippetEntity.setUserEntity(mainUser);
        Long snippedId = codeSnippetEntityRepository.save(codeSnippetEntity).getId();

        assertEquals(1, codeSnippetEntityRepository.count());

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_API_URL + "/delete/" + snippedId)
                        .header("Authorization", "Bearer " + mainUserJwtToken))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(0, codeSnippetEntityRepository.count());
        assertFalse(codeSnippetEntityRepository.existsById(snippedId));
    }

    @Test
    @DisplayName("TEST code snippet delete error snippet id not exists")
    void codeSnippetDelete_errorSnippetIdNotExists() throws Exception {
        Long id = 9999L;
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(BASE_API_URL + "/delete/" + id)
                        .header("Authorization", "Bearer " + mainUserJwtToken))
                .andExpect(status().isNotFound())
                .andReturn();

        Exception resolvedException = result.getResolvedException();

        Assertions.assertNotNull(resolvedException);
        Assertions.assertInstanceOf(IdNotFoundException.class, resolvedException);
        Assertions.assertEquals(ExceptionMessages.ID_NOT_FOUND, resolvedException.getMessage());

        assertFalse(codeSnippetEntityRepository.existsById(id));
    }

    @Test
    @DisplayName("TEST code snippet delete error different owner")
    void codeSnippetDelete_errorCauseOfDifferentOwner() throws Exception {
        CodeSnippetEntity codeSnippetEntity = new CodeSnippetEntity();
        codeSnippetEntity.setCreationDatetime(LocalDateTime.now());
        codeSnippetEntity.setLanguageEntity(programmingLanguage);
        codeSnippetEntity.setCodeText("void testFunc(){}");
        codeSnippetEntity.setTitle("Title.JavaTest");
        codeSnippetEntity.setUserEntity(secondaryUser);
        Long snippedId = codeSnippetEntityRepository.save(codeSnippetEntity).getId();

        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.delete(BASE_API_URL + "/delete/" + snippedId)
                        .header("Authorization", "Bearer " + mainUserJwtToken))
                .andExpect(status().isUnauthorized())
                .andReturn();

        Exception resolvedException = result1.getResolvedException();

        Assertions.assertNotNull(resolvedException);
        Assertions.assertInstanceOf(NotTheOwnerException.class, resolvedException);
        Assertions.assertEquals(ExceptionMessages.NOT_THE_OWNER, resolvedException.getMessage());

        assertEquals(1, codeSnippetEntityRepository.count());
        assertTrue(codeSnippetEntityRepository.existsById(snippedId));
    }

    @Test
    @DisplayName("TEST user code snippets list success")
    void codeSnippetList_success() throws Exception {
        String codeText = "void testFunc(){}";
        CodeSnippetEntity codeSnippetEntity = new CodeSnippetEntity();
        codeSnippetEntity.setCreationDatetime(LocalDateTime.now());
        codeSnippetEntity.setLanguageEntity(programmingLanguage);
        codeSnippetEntity.setCodeText(codeText);
        codeSnippetEntity.setTitle("Title.JavaTest");
        codeSnippetEntity.setUserEntity(mainUser);
        codeSnippetEntityRepository.save(codeSnippetEntity);

        CodeSnippetEntity codeSnippetEntity2 = new CodeSnippetEntity();
        codeSnippetEntity2.setCreationDatetime(LocalDateTime.now());
        codeSnippetEntity2.setLanguageEntity(programmingLanguage);
        codeSnippetEntity2.setCodeText(codeText);
        codeSnippetEntity2.setTitle("Title.JavaTest");
        codeSnippetEntity2.setUserEntity(mainUser);
        codeSnippetEntityRepository.save(codeSnippetEntity2);

        assertEquals(2, codeSnippetEntityRepository.count());

        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.get(BASE_API_URL + "/list")
                        .header("Authorization", "Bearer " + mainUserJwtToken))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse1 = result1.getResponse().getContentAsString();
        List<CodeSnippetDto> codeSnippetDtos = objectMapper.readValue(
                jsonResponse1,
                new TypeReference<List<CodeSnippetDto>>() {}
        );

        for (CodeSnippetDto c : codeSnippetDtos) {
            assertEquals(mainUser.getId(), c.getUserId());
            assertEquals(codeText, c.getCodeText());
        }
    }

    @Test
    @DisplayName("TEST user code snippets get success")
    void codeSnippetList_getSuccess() throws Exception {
        String codeText = "void testFunc(){}";
        CodeSnippetEntity codeSnippetEntity = new CodeSnippetEntity();
        codeSnippetEntity.setCreationDatetime(LocalDateTime.now());
        codeSnippetEntity.setLanguageEntity(programmingLanguage);
        codeSnippetEntity.setCodeText(codeText);
        codeSnippetEntity.setTitle("Title.JavaTest");
        codeSnippetEntity.setUserEntity(mainUser);
        Long snippetId = codeSnippetEntityRepository.save(codeSnippetEntity).getId();

        assertEquals(1, codeSnippetEntityRepository.count());

        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.get(BASE_API_URL + "/get/" + snippetId)
                        .header("Authorization", "Bearer " + mainUserJwtToken))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse1 = result1.getResponse().getContentAsString();
        CodeSnippetDto codeSnippetDtoRes = objectMapper.readValue(jsonResponse1, CodeSnippetDto.class);

        assertEquals(snippetId, codeSnippetDtoRes.getId());
        assertEquals(codeText, codeSnippetDtoRes.getCodeText());
    }

    @Test
    @DisplayName("TEST user code snippets get error not the owner")
    void codeSnippetList_getErrorNotTheOwner() throws Exception {
        CodeSnippetEntity codeSnippetEntity = new CodeSnippetEntity();
        codeSnippetEntity.setCreationDatetime(LocalDateTime.now());
        codeSnippetEntity.setLanguageEntity(programmingLanguage);
        codeSnippetEntity.setCodeText("void testFunc(){}");
        codeSnippetEntity.setTitle("Title.JavaTest");
        codeSnippetEntity.setUserEntity(secondaryUser);
        Long snippedId = codeSnippetEntityRepository.save(codeSnippetEntity).getId();

        assertEquals(1, codeSnippetEntityRepository.count());

        MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.get(BASE_API_URL + "/get/" + snippedId)
                        .header("Authorization", "Bearer " + mainUserJwtToken))
                .andExpect(status().isUnauthorized())
                .andReturn();

        Exception resolvedException = result1.getResolvedException();

        Assertions.assertNotNull(resolvedException);
        Assertions.assertInstanceOf(NotTheOwnerException.class, resolvedException);
        Assertions.assertEquals(ExceptionMessages.NOT_THE_OWNER, resolvedException.getMessage());
    }

     */
}
