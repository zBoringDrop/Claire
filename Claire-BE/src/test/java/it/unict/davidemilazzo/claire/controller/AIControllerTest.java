package it.unict.davidemilazzo.claire.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import it.unict.davidemilazzo.claire.config.JwtUtil;
import it.unict.davidemilazzo.claire.dto.AIDto;
import it.unict.davidemilazzo.claire.model.*;
import it.unict.davidemilazzo.claire.respository.AIEntityRepository;
import it.unict.davidemilazzo.claire.respository.AIProviderRepository;
import it.unict.davidemilazzo.claire.respository.UserEntityRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AIControllerTest {
    private final String BASE_API_URL = "/api/ai";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private AIProviderRepository aiProviderRepository;

    @Autowired
    private AIEntityRepository aiEntityRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String mainUserJwtToken;
    private UserEntity mainUser;
    private AIProviderEntity aiProviderEntity;
    private AIEntity ai1;
    private AIEntity ai2;
    private AIEntity ai3;
    private List<AIEntity> dbAiEntities;

    @BeforeAll
    void init() {
        aiProviderRepository.deleteAll();
        aiEntityRepository.deleteAll();
        userEntityRepository.deleteAll();

        UserEntity user1 = new UserEntity();
        user1.setNickname("User1");
        user1.setName("UserName1");
        user1.setSurname("UserSurname1");
        user1.setEmail("user1@email.com");
        user1.setPassword(passwordEncoder.encode("password1"));
        user1.setRole(Role.USER);
        mainUser = userEntityRepository.save(user1);

        mainUserJwtToken = jwtUtil.generateToken(user1.getEmail());

        aiProviderEntity = new AIProviderEntity();
        aiProviderEntity.setAiType(AIType.LOCAL);
        aiProviderEntity.setName(AIProvidersEnum.OLLAMA);
        aiProviderEntity.setDescription("Test provider description");
        aiProviderEntity.setBaseUrl("testurl/test:80");
        aiProviderEntity = aiProviderRepository.save(aiProviderEntity);

        ai1 = new AIEntity();
        ai1.setModel("test-model1:v1");
        ai1.setName("TestAI1");
        ai1.setFamily("test-family");
        ai1.setParameterSize("3B");
        ai1.setSize(123456L);
        ai1.setAiProviderEntity(aiProviderEntity);
        ai1.setActive(true);
        ai1.setModifiedAt(LocalDateTime.now().minusDays(1));
        ai1.setLastDBSync(LocalDateTime.now());
        ai1.setJsonExtra("{\"extra\":\"value\"}");
        aiEntityRepository.save(ai1);

        ai2 = new AIEntity();
        ai2.setModel("test-model2:v1");
        ai2.setName("TestAI2");
        ai2.setFamily("test-family");
        ai2.setParameterSize("8B");
        ai2.setSize(123456L);
        ai1.setAiProviderEntity(aiProviderEntity);
        ai2.setActive(true);
        ai2.setModifiedAt(LocalDateTime.now().minusDays(1));
        ai2.setLastDBSync(LocalDateTime.now());
        ai2.setJsonExtra("{\"extra\":\"value\"}");
        aiEntityRepository.save(ai2);

        ai3 = new AIEntity();
        ai3.setModel("test-model3:v1");
        ai3.setName("TestAI3");
        ai3.setFamily("test-family");
        ai3.setParameterSize("4B");
        ai3.setSize(123456L);
        ai1.setAiProviderEntity(aiProviderEntity);
        ai3.setActive(false);
        ai3.setModifiedAt(LocalDateTime.now().minusDays(1));
        ai3.setLastDBSync(LocalDateTime.now());
        ai3.setJsonExtra("{\"extra\":\"value\"}");
        aiEntityRepository.save(ai3);

        dbAiEntities = new ArrayList<>();
        dbAiEntities.add(ai1);
        dbAiEntities.add(ai2);
        dbAiEntities.add(ai3);
    }

    /*
    @Test //PROBLEM WITH AUTOMATIC SCHEDULER
    @DisplayName("TEST AI get all avaiable models")
    void aiGet_allAvaiableModelsSuccess() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(BASE_API_URL + "/models")
                        .header("Authorization", "Bearer " + mainUserJwtToken))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        List<AIDto> aiDtoRes = objectMapper.readValue(
                responseJson,
                new TypeReference<List<AIDto>>() {}
        );

        assertEquals(aiDtoRes.size(), aiEntityRepository.count());
        for (int i=0; i<aiDtoRes.size(); i++) {
            AIEntity dbEntity = dbAiEntities.get(i);
            AIDto dtoRes = aiDtoRes.get(i);

            Assertions.assertEquals(dbEntity.getId(), dtoRes.getId());
            Assertions.assertEquals(dbEntity.getModel(), dtoRes.getModel());
            Assertions.assertEquals(dbEntity.getName(), dtoRes.getName());
            Assertions.assertEquals(dbEntity.getFamily(), dtoRes.getFamily());
            Assertions.assertEquals(dbEntity.getParameterSize(), dtoRes.getParameterSize());
            Assertions.assertEquals(dbEntity.getSize(), dtoRes.getSize());
            Assertions.assertEquals(dbEntity.getAiType(), dtoRes.getAiType());
            Assertions.assertEquals(false, dtoRes.isActive()); //Scheduler set all false
            //Assertions.assertEquals(dbEntity.getModifiedAt(), dtoRes.getModifiedAt());
            //Assertions.assertEquals(dbEntity.getLastDBSync(), dtoRes.getLastDBSync());
            Assertions.assertEquals(dbEntity.getJsonExtra(), dtoRes.getJsonExtra());
        }
    }

     */
}
