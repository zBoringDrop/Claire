package it.unict.davidemilazzo.claire.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import it.unict.davidemilazzo.claire.ai.ExampleCodeSnippets;
import it.unict.davidemilazzo.claire.config.JwtUtil;
import it.unict.davidemilazzo.claire.model.*;
import it.unict.davidemilazzo.claire.respository.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CodeSnippetAnalysisControllerTest {

    private final String BASE_API_URL = "/api/analysis/codesnippet";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private AnalysisEntityRepository analysisEntityRepository;

    @Autowired
    private CodeSnippetEntityRepository codeSnippetEntityRepository;

    @Autowired
    private AIProviderRepository aiProviderRepository;

    @Autowired
    private ProgrammingLanguageRepository programmingLanguageRepository;

    @Autowired
    private FileEntityRepository fileEntityRepository;

    @Autowired
    private AIEntityRepository aiEntityRepository;

    @Autowired
    private StaticToolRepository staticToolRepository;

    @Autowired
    private AnalysisCategoryRepository analysisCategoryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    private UserEntity user1;
    private String mainUserJwtToken;
    private AIEntity ollamaAI;
    private AIEntity geminiAI;
    private ProgrammingLanguageEntity programmingLanguage;
    private ProgrammingLanguageEntity programmingLanguageDefault;
    private AIProviderEntity aiProviderEntity;
    private FileEntity file1;
    private CodeSnippetEntity snippet1;
    private AnalysisEntity analysis1;
    private AnalysisEntity analysis2;
    private AnalysisEntity analysis3;
    private AnalysisEntity analysis4;
    private List<AnalysisEntity> allAnalyses;
    private AnalysisCategoryEntity categorySecurity;
    private AnalysisCategoryEntity categoryReliability;
    private AnalysisCategoryEntity categoryPerformance;
    private AnalysisCategoryEntity categoryArchitecture;
    private AnalysisCategoryEntity categoryStructure;
    private AnalysisCategoryEntity categoryNaming;

    @BeforeAll
    void init() throws JsonProcessingException {
        aiProviderRepository.deleteAll();
        programmingLanguageRepository.deleteAll();
        analysisEntityRepository.deleteAll();
        codeSnippetEntityRepository.deleteAll();
        fileEntityRepository.deleteAll();
        aiEntityRepository.deleteAll();
        userEntityRepository.deleteAll();
        staticToolRepository.deleteAll();

        ObjectMapper mapper = new ObjectMapper();

        user1 = new UserEntity();
        user1.setNickname("User1");
        user1.setName("UserName1");
        user1.setSurname("UserSurname1");
        user1.setEmail("user1@email.com");
        user1.setPassword(passwordEncoder.encode("password1"));
        user1.setRole(Role.USER);
        user1 = userEntityRepository.save(user1);
        mainUserJwtToken = jwtUtil.generateToken(user1.getEmail());

        programmingLanguage = new ProgrammingLanguageEntity();
        programmingLanguage.setName("JavaTest");
        programmingLanguage = programmingLanguageRepository.save(programmingLanguage);

        programmingLanguageDefault = new ProgrammingLanguageEntity();
        programmingLanguageDefault.setName("PSEUDO_CODE");
        programmingLanguageDefault = programmingLanguageRepository.saveAndFlush(programmingLanguageDefault);

        file1 = new FileEntity();
        file1.setUserEntity(user1);
        file1.setFilename("file1.javaTest");
        file1.setUploadDatetime(LocalDateTime.now());
        file1.setLanguageEntity(programmingLanguage);
        file1.setContentType("text/plain");
        file1.setSize(2048);
        file1.setData("System.out.println(\"Hello World\");");
        file1 = fileEntityRepository.save(file1);

        snippet1 = new CodeSnippetEntity();
        snippet1.setUserEntity(user1);
        snippet1.setCreationDatetime(LocalDateTime.now().minusDays(1));
        snippet1.setLanguageEntity(programmingLanguage);
        snippet1.setTitle("Title.JavaTest");
        snippet1.setCodeText(ExampleCodeSnippets.Java_SQLInjection);
        snippet1 = codeSnippetEntityRepository.save(snippet1);

        aiProviderEntity = new AIProviderEntity();
        aiProviderEntity.setAiType(AIType.LOCAL);
        aiProviderEntity.setName(AIProvidersEnum.OLLAMA);
        aiProviderEntity.setDescription("Test provider description");
        aiProviderEntity.setBaseUrl("testurl/test:80");
        aiProviderEntity = aiProviderRepository.save(aiProviderEntity);

        ollamaAI = new AIEntity();
        ollamaAI.setModel("deepcoder:1.5b");
        ollamaAI.setName("deepcoder:1.5b");
        ollamaAI.setFamily("deepseek-coder");
        ollamaAI.setParameterSize("1.5B");
        ollamaAI.setSize(776_000_000L);
        ollamaAI.setType(ToolType.AI);
        ollamaAI.setAiProviderEntity(aiProviderEntity);
        ollamaAI.setActive(true);
        ollamaAI.setModifiedAt(LocalDateTime.now());
        ollamaAI.setLastDBSync(LocalDateTime.now());
        ollamaAI.setJsonExtra("{}");
        ollamaAI = aiEntityRepository.save(ollamaAI);

        geminiAI = new AIEntity();
        geminiAI.setModel("gemini-2.5-flash");
        geminiAI.setName("gemini-2.5-flash");
        geminiAI.setFamily("gemini");
        geminiAI.setParameterSize("500B");
        geminiAI.setSize(776_000_000L);
        geminiAI.setType(ToolType.AI);
        geminiAI.setType(ToolType.AI);
        geminiAI.setAiProviderEntity(aiProviderEntity);
        geminiAI.setActive(true);
        geminiAI.setModifiedAt(LocalDateTime.now());
        geminiAI.setLastDBSync(LocalDateTime.now());
        geminiAI.setJsonExtra("{}");
        geminiAI = aiEntityRepository.save(geminiAI);

        analysis1 = new AnalysisEntity();
        analysis1.setFileEntity(file1);
        analysis1.setStatus(AnalysisStatus.COMPLETED);
        analysis1.setCodeSnippetEntity(null);
        analysis1.setToolEntity(ollamaAI);
        analysis1.setResultJson(mapper.readTree("{\"score\": 85}"));
        analysis1.setCreatedAt(LocalDateTime.now().minusHours(4));
        analysis1 = analysisEntityRepository.save(analysis1);

        analysis2 = new AnalysisEntity();
        analysis2.setFileEntity(file1);
        analysis2.setStatus(AnalysisStatus.COMPLETED);
        analysis2.setCodeSnippetEntity(null);
        analysis2.setToolEntity(ollamaAI);
        analysis2.setResultJson(mapper.readTree("{\"score\": 90}"));
        analysis2.setCreatedAt(LocalDateTime.now().minusHours(3));
        analysis2 = analysisEntityRepository.save(analysis2);

        analysis3 = new AnalysisEntity();
        analysis3.setCodeSnippetEntity(snippet1);
        analysis3.setStatus(AnalysisStatus.COMPLETED);
        analysis3.setFileEntity(null);
        analysis3.setToolEntity(ollamaAI);
        analysis3.setResultJson(mapper.readTree("{\"score\": 75}"));
        analysis3.setCreatedAt(LocalDateTime.now().minusHours(2));
        analysis3 = analysisEntityRepository.save(analysis3);

        analysis4 = new AnalysisEntity();
        analysis4.setCodeSnippetEntity(snippet1);
        analysis4.setStatus(AnalysisStatus.COMPLETED);
        analysis4.setFileEntity(null);
        analysis4.setToolEntity(ollamaAI);
        analysis4.setResultJson(mapper.readTree("{\"score\": 80}"));
        analysis4.setCreatedAt(LocalDateTime.now().minusHours(1));
        analysis4 = analysisEntityRepository.save(analysis4);

        allAnalyses = new ArrayList<>();
        allAnalyses.add(analysis1);
        allAnalyses.add(analysis2);
        allAnalyses.add(analysis3);
        allAnalyses.add(analysis4);
        allAnalyses.sort(Comparator.comparing(AnalysisEntity::getCreatedAt).reversed());

        categorySecurity = analysisCategoryRepository.findByType(AnalysisCategoryType.SECURITY);
        categoryReliability = analysisCategoryRepository.findByType(AnalysisCategoryType.RELIABILITY_AND_BUGS);
        categoryPerformance = analysisCategoryRepository.findByType(AnalysisCategoryType.PERFORMANCE);
        categoryArchitecture = analysisCategoryRepository.findByType(AnalysisCategoryType.ARCHITECTURE_AND_DESIGN);
        categoryStructure = analysisCategoryRepository.findByType(AnalysisCategoryType.CODE_STRUCTURE_AND_COMPLEXITY);
        categoryNaming = analysisCategoryRepository.findByType(AnalysisCategoryType.NAMING_AND_DOCUMENTATION);
    }

    /*
    @Test
    @DisplayName("TEST user get all analyses performed on all code snippets success")
    void userAnalysis_getAllPerformedOnAllCodeSnippetsSuccess() throws Exception {
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get(BASE_API_URL + "/show/all")
                                .header("Authorization", "Bearer " + mainUserJwtToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        List<AnalysisDto> analysisDtosRes = objectMapper.readValue(
                responseJson,
                new TypeReference<List<AnalysisDto>>() {}
        );

        List<AnalysisEntity> codeSnippetAnalyses = allAnalyses.stream()
                .filter(a -> a.getFileEntity() == null)
                .toList();

        Assertions.assertEquals(codeSnippetAnalyses.size(), analysisDtosRes.size());

        for (int i=0; i<codeSnippetAnalyses.size(); i++) {
            AnalysisEntity dbAnalysis = codeSnippetAnalyses.get(i);
            AnalysisDto analysisDto = analysisDtosRes.get(i);

            Assertions.assertEquals(dbAnalysis.getId(), analysisDto.getId());
            Assertions.assertEquals(dbAnalysis.getCodeSnippetEntity().getId(), analysisDto.getCodeSnippetId());
            Assertions.assertEquals(dbAnalysis.getResultJson(), new ObjectMapper().readTree(analysisDto.getResultJson().asText()));
            Assertions.assertEquals(dbAnalysis.getToolEntity().getId(), analysisDto.getToolId());
            Assertions.assertNull(analysisDto.getFileId());
        }
    }

    @Test
    @DisplayName("TEST user get all analyses performed on a specific code snippet success")
    void userAnalysis_getAllPerformedOnASpecificCodeSnippetsSuccess() throws Exception {
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get(BASE_API_URL + "/show/" + snippet1.getId())
                                .header("Authorization", "Bearer " + mainUserJwtToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        List<AnalysisDto> analysisDtosRes = objectMapper.readValue(
                responseJson,
                new TypeReference<List<AnalysisDto>>() {}
        );

        List<AnalysisEntity> codeSnippetAnalyses = allAnalyses.stream()
                .filter(a -> a.getFileEntity() == null && a.getCodeSnippetEntity().getId().equals(snippet1.getId()))
                .toList();

        Assertions.assertEquals(2, analysisDtosRes.size());

        for (int i=0; i<codeSnippetAnalyses.size(); i++) {
            AnalysisEntity dbAnalysis = codeSnippetAnalyses.get(i);
            AnalysisDto analysisDto = analysisDtosRes.get(i);

            Assertions.assertEquals(dbAnalysis.getId(), analysisDto.getId());
            Assertions.assertEquals(dbAnalysis.getCodeSnippetEntity().getId(), analysisDto.getCodeSnippetId());
            Assertions.assertEquals(dbAnalysis.getResultJson(), new ObjectMapper().readTree(analysisDto.getResultJson().asText()));
            Assertions.assertEquals(dbAnalysis.getToolEntity().getId(), analysisDto.getToolId());
            Assertions.assertNull(analysisDto.getFileId());
        }
    }

    @Test
    @DisplayName("TEST user start an analysis using Ollama (local) on a specific code snippet success")
    void userAnalysis_startAnalysisUsingOllamaLocalOnASpecificCodeSnippetsSuccess() throws Exception {
        AnalysisRequestDto analysisRequestDto = new AnalysisRequestDto();
        analysisRequestDto.setToolId(geminiAI.getId());
        analysisRequestDto.setSourceId(snippet1.getId());
        analysisRequestDto.setAnalysisCategoryIds(Set.of(categoryNaming.getId(), categoryStructure.getId()));

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Runnable runnableTask = () -> {
            try {
                List<AnalysisDto> analysisDtoList;
                while (true) {
                    analysisDtoList = logAnalysisStatus();
                    for (AnalysisDto analysisDto : analysisDtoList) {
                        System.out.println(analysisDto);
                    }
                    System.out.println("");
                    TimeUnit.SECONDS.sleep(2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        executorService.execute(runnableTask);

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post(BASE_API_URL + "/analyze")
                                .header("Authorization", "Bearer " + mainUserJwtToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(analysisRequestDto))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        log.info("RESPONSE LONG: {}", responseJson);

        executorService.shutdown();
        executorService.awaitTermination(50, TimeUnit.SECONDS);


        Assertions.assertEquals(allAnalyses.size()+1, analysisEntityRepository.count());
        Assertions.assertTrue(analysisEntityRepository.existsById(analysisDtoRes.getId()));
        AnalysisEntity newAnalysis = analysisEntityRepository.findById(analysisDtoRes.getId()).get();
        Assertions.assertEquals(analysisRequestDto.getToolId(), newAnalysis.getToolEntity().getId());
        Assertions.assertEquals(analysisRequestDto.getSourceId(), newAnalysis.getCodeSnippetEntity().getId());
        Assertions.assertNotNull(newAnalysis.getResultJson());


    }

    @Test
    @DisplayName("TEST user start an analysis using Gemini Cloud on a specific code snippet success")
    void userAnalysis_startAnalysisUsingGeminiCloudOnASpecificCodeSnippetsSuccess() throws Exception {
        AnalysisRequestDto analysisRequestDto = new AnalysisRequestDto();
        analysisRequestDto.setToolId(geminiAI.getId());
        analysisRequestDto.setSourceId(snippet1.getId());
        analysisRequestDto.setAnalysisCategoryIds(Set.of(categoryNaming.getId(), categoryStructure.getId()));

        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.post(BASE_API_URL + "/analyze")
                                .header("Authorization", "Bearer " + mainUserJwtToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(analysisRequestDto))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        log.info("RESPONSE JSON: {}", responseJson);
        AnalysisDto analysisDtoRes = objectMapper.readValue(responseJson, AnalysisDto.class);
        //log.info("RESPONSE DTO: {}", analysisDtoRes.toString());

        Assertions.assertEquals(allAnalyses.size()+1, analysisEntityRepository.count());
        Assertions.assertTrue(analysisEntityRepository.existsById(analysisDtoRes.getId()));
        AnalysisEntity newAnalysis = analysisEntityRepository.findById(analysisDtoRes.getId()).get();
        Assertions.assertEquals(analysisRequestDto.getToolId(), newAnalysis.getToolEntity().getId());
        Assertions.assertEquals(analysisRequestDto.getSourceId(), newAnalysis.getCodeSnippetEntity().getId());
        Assertions.assertNotNull(newAnalysis.getResultJson());
    }

    List<AnalysisDto> logAnalysisStatus() throws Exception {
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/analysis/all")
                                .header("Authorization", "Bearer " + mainUserJwtToken)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        List<AnalysisDto> analysisDtosRes = objectMapper.readValue(
                responseJson,
                new TypeReference<List<AnalysisDto>>() {}
        );

        return analysisDtosRes;
    }
    */
}
