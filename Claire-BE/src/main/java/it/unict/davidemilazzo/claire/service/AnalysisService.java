package it.unict.davidemilazzo.claire.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.unict.davidemilazzo.claire.ai.*;
import it.unict.davidemilazzo.claire.ai.ollama.OllamaConfigurations;
import it.unict.davidemilazzo.claire.ai.ollama.OllamaSpecificConfig;
import it.unict.davidemilazzo.claire.ai.ollama.UserOllamaCustomConfigDto;
import it.unict.davidemilazzo.claire.dao.AnalysisDao;
import it.unict.davidemilazzo.claire.dto.*;
import it.unict.davidemilazzo.claire.exception.CategoryNotFoundException;
import it.unict.davidemilazzo.claire.exception.ExceptionMessages;
import it.unict.davidemilazzo.claire.exception.IdNotFoundException;
import it.unict.davidemilazzo.claire.exception.NotTheOwnerException;
import it.unict.davidemilazzo.claire.model.AnalysisCategoryType;
import it.unict.davidemilazzo.claire.model.AnalysisStatus;
import it.unict.davidemilazzo.claire.util.AIUtils;
import it.unict.davidemilazzo.claire.util.CodeManagement;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AnalysisService {

    private static final Logger log = LogManager.getLogger(AnalysisService.class);
    private final AnalysisDao analysisDao;
    private final FileService fileService;
    private final CodeSnippetService codeSnippetService;
    private final AIService aiService;
    private final AnalysisCategoryService analysisCategoryService;
    private final AIProviderService aiProviderService;
    private final UserOllamaCustomConfigService userOllamaCustomConfigService;
    private final AnalyserFactory analyserFactory;

    private final ApplicationContext applicationContext;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Transactional
    public AnalysisDto createNew(AnalysisDto analysisDto) {
        //log.info("DTO da creare: {}", analysisDto.toString());
        return analysisDao.createNew(analysisDto);
    }

    @Transactional
    public AnalysisDto initializeNew(AnalysisStatus status) {
        AnalysisDto analysisDto = new AnalysisDto();
        analysisDto.setCreatedAt(LocalDateTime.now());
        analysisDto.setStatus(status);
        return createNew(analysisDto);
    }

    @Transactional
    public AnalysisDto delete(Long analysisId) {
        return analysisDao.delete(analysisId);
    }

    @Transactional
    public AnalysisDto update(AnalysisDto analysisDto) {
        return analysisDao.update(analysisDto);
    }

    public List<AnalysisDto> findByStatus(AnalysisStatus status) {
        return analysisDao.findByStatus(status);
    }

    public List<AnalysisDto> getAllUserFileAnalysis(Long userId) { //TODO Fare query custom
        List<FileDto> userFileDtos = fileService.listUserFiles(userId);

        List<AnalysisDto> userFileAnalysisDtos = new ArrayList<>();
        for (FileDto file : userFileDtos) {
            userFileAnalysisDtos.addAll(analysisDao.findByFileEntity_id(file.getId()));
        }

        sortAnalysisByDateDesc(userFileAnalysisDtos);
        return userFileAnalysisDtos;
    }

    public List<AnalysisDto> getAllUserCodeSnippetAnalysis(Long userId) {   //TODO Fare query custom
        List<CodeSnippetDto> userCodeSnippetDtos = codeSnippetService.listUserCodeSnippet(userId);

        List<AnalysisDto> userCodeSnippetAnalysisDtos = new ArrayList<>();
        for (CodeSnippetDto snippet : userCodeSnippetDtos) {
            userCodeSnippetAnalysisDtos.addAll(analysisDao.findByCodeSnippetEntity_id(snippet.getId()));
        }

        sortAnalysisByDateDesc(userCodeSnippetAnalysisDtos);
        return userCodeSnippetAnalysisDtos;
    }

    public static void sortAnalysisByDateDesc(List<AnalysisDto> list) {
        list.sort(Comparator.comparing(AnalysisDto::getCreatedAt).reversed());
    }

    public List<AnalysisDto> getAllUserAnalysis(Long userId) {
        return Stream.concat(
                        getAllUserFileAnalysis(userId).stream(),
                        getAllUserCodeSnippetAnalysis(userId).stream()
                )
                .sorted(Comparator.comparing(AnalysisDto::getCreatedAt).reversed())
                .toList();
    }

    public List<AnalysisDto> getUserAnalysisByStatus(Long userId, AnalysisStatus status) {
        return getAllUserAnalysis(userId).stream()
                .filter(analysis -> analysis.getStatus() == status)
                .toList();
    }

    public List<AnalysisPreviewDto> findAllPreviewsByUserAndStates(Long userId, List<AnalysisStatus> analysisStatuses) {
        return analysisDao.findAllPreviewsByUserAndStates(userId, analysisStatuses);
    }

    public AnalysisDto findById(Long id) {
        return analysisDao.findById(id);
    }

    public boolean exists(Long analysisId) {
        return analysisDao.exists(analysisId);
    }

    public List<AnalysisDto> getUserCodeSnippetAnalysis(Long userId, Long codeSnippetId) {
        Long codeSnippetUserId = codeSnippetService.findById(codeSnippetId).getUserId();
        if (!Objects.equals(codeSnippetUserId, userId)) {
            throw new NotTheOwnerException(ExceptionMessages.NOT_THE_OWNER);
        }

        List<AnalysisDto> result = analysisDao.findByCodeSnippetEntity_id(codeSnippetId);
        result.sort(Comparator.comparing(AnalysisDto::getCreatedAt).reversed());
        return result;
    }

    public List<AnalysisDto> getUserFileAnalysis(Long userId, Long fileId) {
        Long fileUserId = fileService.findById(fileId).getUserId();
        if (!Objects.equals(fileUserId, userId)) {
            throw new NotTheOwnerException(ExceptionMessages.NOT_THE_OWNER);
        }

        List<AnalysisDto> result = analysisDao.findByFileEntity_id(fileId);
        result.sort(Comparator.comparing(AnalysisDto::getCreatedAt).reversed());
        return result;
    }

    public List<AnalysisPreviewDto> getAllUserPreviews(Long userId) {
        return analysisDao.getAllUserPreviews(userId);
    }

    public List<AnalysisPreviewDto> findAllPreviewsByUserAndStatus(Long userId, AnalysisStatus status) {
        return analysisDao.findAllPreviewsByUserAndStatus(userId, status);
    }

    public List<AnalysisPreviewDto> findAllPreviewsByUserAndFileId(Long userId, Long fileId) {
        return analysisDao.findAllPreviewsByUserAndFileId(userId, fileId);
    }

    public List<AnalysisPreviewDto> findAllPreviewsByUserAndSnippetId(Long userId, Long fileId) {
        return analysisDao.findAllPreviewsByUserAndSnippetId(userId, fileId);
    }

    public AiProviderConfig getProviderConfig(Long userId, Long providerId, String paramSize) {
        if (aiProviderService.findById(providerId).getName() == AIProvidersEnum.OLLAMA) {
            try {
                UserOllamaCustomConfigDto configDto = userOllamaCustomConfigService.findByUserId(userId);

                if (configDto != null && configDto.getUseCustomConfig()) {
                    log.info("Using user custom config snapshot for this analysis");
                    return new OllamaSpecificConfig(configDto);
                } else {
                    log.info("Using system default config");
                    double modelSize = AIUtils.extractModelSize(paramSize);
                    return new OllamaSpecificConfig(OllamaConfigurations.getDefaultConfigBySize(modelSize));
                }
            } catch (IdNotFoundException e) {
                log.info("Exception: user {} has not Ollama config record. Using system default config", userId);
                double modelSize = AIUtils.extractModelSize(paramSize);
                return new OllamaSpecificConfig(OllamaConfigurations.getDefaultConfigBySize(modelSize));
            }
        }
        return null;
    }

    public AnalysisDto asyncAnalyzeCodeSnippet(AnalysisRequestDto analysisRequestDto,
                                                Long userId) {
        AnalysisDto analysisDto = initializeNew(AnalysisStatus.VALIDATING);

        applicationContext.getBean(AnalysisService.class)
                .analyzeCodeSnippet(analysisRequestDto, userId, analysisDto.getId());

        return analysisDto;
    }

    public AnalysisDto asyncAnalyzeFile(AnalysisRequestDto analysisRequestDto,
                                        Long userId) {
        AnalysisDto analysisDto = initializeNew(AnalysisStatus.VALIDATING);

        applicationContext.getBean(AnalysisService.class).
                analyzeFile(analysisRequestDto, userId, analysisDto.getId());

        return analysisDto;
    }

    @Async
    public void analyzeCodeSnippet(AnalysisRequestDto analysisRequestDto,
                                  Long userId,
                                  Long analysisId) {

        try {
            CodeSnippetDto codeSnippetDto = codeSnippetService.findById(analysisRequestDto.getSourceId());
            validateRequest(userId, codeSnippetDto.getUserId(), analysisRequestDto.getAnalysisCategoryIds());

            List<AnalysisCategoryDto> analysisCategoryDtos = analysisRequestDto.getAnalysisCategoryIds().stream()
                    .map(analysisCategoryService::findById)
                    .toList();

            AIDto aiDto = aiService.findById(analysisRequestDto.getToolId());
            AiProviderConfig aiConfig = getProviderConfig(userId, aiDto.getAiProviderId(), aiDto.getParameterSize());
            AnalyserInt aiAnalyzer = analyserFactory.getAnalyser(aiDto.getAiProviderId(), userId);

            update(AnalysisDto.builder()
                    .id(analysisId)
                    .status(AnalysisStatus.IN_PROGRESS)
                    .message(AnalysisStatus.IN_PROGRESS.toString())
                    .fileId(null)
                    .codeSnippetId(codeSnippetDto.getId())
                    .toolId(aiDto.getId())
                    .analysisCategoryIds(extractCategoryIds(analysisCategoryDtos))
                    .resultJson(null)
                    .createdAt(null)
                    .build());

            List<PromptGenerationResult> prompts =
                    SpecializedAnalysisPrompts.getPromptsByCategory(extractCategoryTypes(analysisCategoryDtos),
                            codeSnippetDto.getCodeText());

            Instant startTime = Instant.now();
            List<ObjectNode> results = startAnalysisForEachCategory(prompts, null, aiDto, aiAnalyzer, analysisId, aiConfig);
            Instant endTime = Instant.now();
            long executionMs = Duration.between(startTime, endTime).toMillis();

            AnalysisMetadata metadata = extractGlobalMetadataFromSnippetResults(results);
            AnalysisOutcome outcome = determineAnalysisOutcome(results);

            if (outcome.getStatus() == AnalysisStatus.FAILED) {
                log.error("Code snippet analysis {} failed. Reason: {}", analysisId, outcome.getMessage());
            } else {
                log.info("Code snippet analysis {} terminated. Status: {}", analysisId, outcome.getMessage());
            }

            ObjectNode finalOutput = getFinalOutput(results, aiConfig);

            update(AnalysisDto.builder()
                    .id(analysisId)
                    .status(outcome.getStatus())
                    .message(outcome.getMessage())
                    .fileId(null)
                    .codeSnippetId(codeSnippetDto.getId())
                    .toolId(aiDto.getId())
                    .analysisCategoryIds(extractCategoryIds(analysisCategoryDtos))
                    .createdAt(null)
                    .endDatetime(LocalDateTime.ofInstant(endTime, ZoneId.of("Europe/Rome")))
                    .executionMs(executionMs)
                    .overallSeverity(metadata.getOverallSeverity())
                    .outputLength(metadata.getOutputLength())
                    .issuesCount(metadata.getIssuesCount())
                    .resultJson(OBJECT_MAPPER.valueToTree(finalOutput))
                    .build());
        } catch (Exception e) {
            update(AnalysisDto.builder()
                    .id(analysisId)
                    .status(AnalysisStatus.FAILED)
                    .message(e.getMessage())
                    .fileId(null)
                    .codeSnippetId(analysisRequestDto.getSourceId())
                    .toolId(analysisRequestDto.getToolId())
                    .analysisCategoryIds(null)
                    .resultJson(null)
                    .createdAt(null)
                    .issuesCount(null)
                    .resultJson(null)
                    .build());
        }

    }

    @Async
    public void analyzeFile(AnalysisRequestDto analysisRequestDto,
                                   Long userId,
                                   Long analysisId) {

        try {
            FileDto fileDto = fileService.findById(analysisRequestDto.getSourceId());
            validateRequest(userId, fileDto.getUserId(), analysisRequestDto.getAnalysisCategoryIds());

            List<AnalysisCategoryDto> analysisCategoryDtos = analysisRequestDto.getAnalysisCategoryIds().stream()
                    .map(analysisCategoryService::findById)
                    .toList();

            AIDto aiDto = aiService.findById(analysisRequestDto.getToolId());
            AiProviderConfig aiConfig = getProviderConfig(userId, aiDto.getAiProviderId(), aiDto.getParameterSize());
            AnalyserInt aiAnalyzer = analyserFactory.getAnalyser(aiDto.getAiProviderId(), userId);

            update(AnalysisDto.builder()
                    .id(analysisId)
                    .status(AnalysisStatus.IN_PROGRESS)
                    .message(AnalysisStatus.IN_PROGRESS.toString())
                    .fileId(fileDto.getId())
                    .codeSnippetId(null)
                    .toolId(aiDto.getId())
                    .analysisCategoryIds(extractCategoryIds(analysisCategoryDtos))
                    .resultJson(null)
                    .createdAt(null)
                    .build());

            List<AnalysisCategoryType> analysisCategoryTypes = extractCategoryTypes(analysisCategoryDtos);
            List<PromptGenerationResult> prompts;

            List<String> codeSnippets = CodeManagement.splitCode(fileDto.getData());
            //log.info("Code snippets {}: \n{}", codeSnippets.size(), codeSnippets.toString());
            List<ObjectNode> allResults = new ArrayList<>(codeSnippets.size());
            HashMap<String, HashSet<String>> foundIssues = new HashMap<>();
            Instant startTime = Instant.now();

            for (int i=0; i<codeSnippets.size(); i++) {
                log.info("Analizing snippet {}/{}", i, codeSnippets.size());
                prompts = SpecializedAnalysisPrompts.getPromptsByCategory(analysisCategoryTypes, codeSnippets.get(i));

                List<ObjectNode> results = startAnalysisForEachCategory(prompts, foundIssues, aiDto, aiAnalyzer, analysisId, aiConfig);
                log.info("Results {}", results);
                log.info("Found issues {}", foundIssues);

                ObjectNode root = OBJECT_MAPPER.createObjectNode();
                ObjectNode meta = OBJECT_MAPPER.createObjectNode();
                root.put("snippet", i);
                meta.set("res", OBJECT_MAPPER.valueToTree(results));
                root.set("meta", meta);
                allResults.add(root);

                //log.info("Root: {}", root);
                //log.info("allResults: {}", allResults);
            }
            //log.info("FINAL allResults: {}", allResults);
            Instant endTime = Instant.now();
            long executionMs = Duration.between(startTime, endTime).toMillis();

            List<ObjectNode> aggregatedResult = aggregateByCategory(allResults);
            AnalysisMetadata metadata = extractGlobalMetadataFromSnippetResults(aggregatedResult);
            AnalysisOutcome outcome = determineAnalysisOutcome(aggregatedResult);

            if (outcome.getStatus() == AnalysisStatus.FAILED) {
                log.error("File analysis {} failed. Reason: {}", analysisId, outcome.getMessage());
            } else {
                log.info("File analysis {} terminated. Status: {}", analysisId, outcome.getMessage());
            }

            ObjectNode finalOutput = getFinalOutput(aggregatedResult, aiConfig);

            update(AnalysisDto.builder()
                    .id(analysisId)
                    .status(outcome.getStatus())
                    .message(outcome.getMessage())
                    .fileId(fileDto.getId())
                    .codeSnippetId(null)
                    .toolId(aiDto.getId())
                    .analysisCategoryIds(extractCategoryIds(analysisCategoryDtos))
                    .createdAt(null)
                    .endDatetime(LocalDateTime.ofInstant(endTime, ZoneId.of("Europe/Rome")))
                    .executionMs(executionMs)
                    .overallSeverity(metadata.getOverallSeverity())
                    .outputLength(metadata.getOutputLength())
                    .issuesCount(metadata.getIssuesCount())
                    .resultJson(OBJECT_MAPPER.valueToTree(finalOutput))
                    .build());
        } catch (Exception e) {
            log.error("Error during file analysis: ", e);
            update(AnalysisDto.builder()
                    .id(analysisId)
                    .status(AnalysisStatus.FAILED)
                    .message(e.getMessage())
                    .fileId(null)
                    .codeSnippetId(null)
                    .toolId(null)
                    .analysisCategoryIds(null)
                    .resultJson(null)
                    .createdAt(null)
                    .issuesCount(null)
                    .resultJson(null)
                    .build());
        }
    }

    private void isOwner(Long userId, Long snippetUserId) {
        if (!Objects.equals(userId, snippetUserId)) {
            throw new NotTheOwnerException(ExceptionMessages.NOT_THE_OWNER);
        }
    }

    private void validateRequest(Long userId, Long sourceUserId, Set<Long> analysisCategoryIds) {
        if (analysisCategoryIds == null || analysisCategoryIds.isEmpty()) {
            throw new CategoryNotFoundException(ExceptionMessages.CATEGORY_NOT_FOUND);
        }
        isOwner(userId, sourceUserId);
    }

    private List<AnalysisCategoryType> extractCategoryTypes(List<AnalysisCategoryDto> analysisCategory) {
        return analysisCategory.stream()
                .map(AnalysisCategoryDto::getType)
                .toList();
    }

    private List<Long> extractCategoryIds(List<AnalysisCategoryDto> analysisCategory) {
        return analysisCategory.stream()
                .map(AnalysisCategoryDto::getId)
                .toList();
    }

    public List<ObjectNode> startAnalysisForEachCategory(List<PromptGenerationResult> prompts,
                                                         HashMap<String, HashSet<String>> foundIssues,
                                                         AIDto aiDto,
                                                         AnalyserInt analyzer,
                                                         Long analysisId,
                                                         AiProviderConfig aiProviderConfig) {
        int total = prompts.size();
        int counter = 0;
        List<ObjectNode> results = new ArrayList<>();

        try {
            for (PromptGenerationResult p : prompts) {
                String message = String.format("Analyzing category '%s' (%d/%d)",
                        p.analysisCategory(), ++counter, total);
                analysisDao.updateStatusMessage(analysisId, message);

                ObjectNode result = analyzeCategory(p, aiDto, analyzer, aiProviderConfig);

                String status = result.path("status").asText();

                if ("completed".equals(status) && foundIssues != null) {

                    HashSet<String> newIssues = extractFoundIssuesFromResults(result);

                    if (foundIssues.containsKey(p.analysisCategory().getDisplayName())) {
                        HashSet<String> actualIssues = foundIssues.get(p.analysisCategory().getDisplayName());
                        log.info("Found prev issues: {}", actualIssues.toString());

                        result = removeDuplicateIssuesFromResult(result, actualIssues);
                        log.info("Result with removed issues: {}", result.toString());

                        actualIssues.addAll(newIssues);
                        foundIssues.replace(p.analysisCategory().getDisplayName(), actualIssues);
                    } else {
                        foundIssues.put(p.analysisCategory().getDisplayName(), newIssues);
                        log.info("Found issues: {}", foundIssues.toString());
                    }

                }

                results.add(result);

            }

        } catch (Exception e) {
            analysisDao.updateStatusMessage(analysisId, "Error: " + e.getMessage());
            analysisDao.updateStatus(analysisId, AnalysisStatus.FAILED);
            log.error("Error analyzing analysisId {}: {}", analysisId, e.getMessage());
        }

        return results;
    }

    private ObjectNode analyzeCategory(PromptGenerationResult categoryAnalysis,
                                       AIDto aiDto,
                                       AnalyserInt aiAnalyzer,
                                       AiProviderConfig aiProviderConfig) {

        Instant startTimer = Instant.now();
        ObjectNode root = OBJECT_MAPPER.createObjectNode();
        ObjectNode meta = OBJECT_MAPPER.createObjectNode();

        try {
            JsonNode result = aiAnalyzer.analyze(
                    AnalysisConfig.builder()
                            .model(aiDto.getModel())
                            .category(categoryAnalysis.analysisCategory().toString())
                            .parameterSize(aiDto.getParameterSize())
                            .prompt(categoryAnalysis.prompt())
                            .responseJson(categoryAnalysis.json())
                            .providerConfig(aiProviderConfig)
                            .build()
            );

            if (result.has("error")) {
                throw new RuntimeException("API Error: " + result.path("error").path("message").asText());
            }

            root.put("status", "completed");
            root.put("output_length", result.toString().length());
            root.set("response", result);

        } catch (Exception e) {
            root.put("status", "error");
            root.put("error_info", e.getMessage());
            root.put("output_length", 0);
            root.putObject("response");

            log.warn("Error during AI analysis in category {}: {}",
                    categoryAnalysis.analysisCategory(), e.getMessage().substring(0, 50));
        } finally {
            Instant endTimer = Instant.now();
            meta.put("category", categoryAnalysis.analysisCategory().toString());
            meta.put("start_datetime", startTimer.toString());
            meta.put("end_datetime", endTimer.toString());
            meta.put("execution_ms", Duration.between(startTimer, endTimer).toMillis());
            meta.put("input_length", categoryAnalysis.prompt().length() + categoryAnalysis.json().length());
            meta.put("prompt", categoryAnalysis.prompt());
            meta.put("json_schema", categoryAnalysis.json());
            root.set("meta", meta);
        }

        return root;
    }

    private static byte calculateSnippetGlobalSeverity(List<ObjectNode> results) {
        double weightedSeveritySum = 0.0;
        int totalIssuesForWeight = 0;

        if (results == null) {
            return 0;
        }

        for (ObjectNode node : results) {
            if (node == null) continue;

            JsonNode response = node.path("response");

            int issuesCount = response.path("issues").size();
            int overallSeverity = response.path("overall_severity").asInt(0);
            int weight = issuesCount > 0 ? issuesCount : 1;

            weightedSeveritySum += overallSeverity * weight;
            totalIssuesForWeight += weight;
        }

        double weightedAverage = totalIssuesForWeight > 0
                ? weightedSeveritySum / totalIssuesForWeight
                : 0.0;

        return (byte) Math.round(weightedAverage);
    }

    public static AnalysisMetadata extractGlobalMetadataFromSnippetResults(List<ObjectNode> results) {
        int totalOutputLength = 0;
        int totalIssuesCount = 0;

        if (results != null) {
            for (ObjectNode node : results) {
                if (node == null) continue;

                totalOutputLength += node.path("output_length").asInt(0);
                totalIssuesCount += node.path("response").path("issues").size();
            }
        }

        byte globalSeverity = calculateSnippetGlobalSeverity(results);

        return new AnalysisMetadata(
                globalSeverity,
                totalOutputLength,
                totalIssuesCount
        );
    }

    private static byte calculateFileGlobalSeverity(List<ObjectNode> results) {
        double weightedSeveritySum = 0.0;
        int totalIssuesForWeight = 0;

        for (ObjectNode snippetNode : results) {
            ArrayNode resArray = (ArrayNode) snippetNode.path("meta").path("res");

            for (JsonNode categoryNode : resArray) {
                ObjectNode response = (ObjectNode) categoryNode.path("response");

                int issuesCount = response.withArray("issues").size();
                int overallSeverity = response.path("overall_severity").asInt();

                int weight = issuesCount > 0 ? issuesCount : 1;

                weightedSeveritySum += overallSeverity * weight;
                totalIssuesForWeight += weight;
            }
        }

        if (totalIssuesForWeight == 0) {
            return 0;
        }

        double weightedAverage = weightedSeveritySum / totalIssuesForWeight;
        return (byte) Math.round(weightedAverage);
    }

    private AnalysisOutcome determineAnalysisOutcome(List<ObjectNode> results) {
        if (results == null || results.isEmpty()) {
            return new AnalysisOutcome(AnalysisStatus.FAILED, "No results generated.");
        }

        long total = results.size();
        long errorsCount = results.stream()
                .filter(node -> "error".equals(node.path("status").asText()))
                .count();

        if (errorsCount == 0) {
            return new AnalysisOutcome(AnalysisStatus.COMPLETED, "Analysis completed successfully");
        }

        if (errorsCount == total) {
            String rawError = results.getFirst().path("error_info").asText(results.getFirst().toString());
            String niceMessage = cleanUniversalErrorMessage(rawError);

            return new AnalysisOutcome(AnalysisStatus.FAILED, "Failed: " + niceMessage);
        }

        return new AnalysisOutcome(
                AnalysisStatus.PARTIAL,
                String.format("Completed with errors: %d/%d categories failed.", errorsCount, total)
        );
    }

    private String cleanUniversalErrorMessage(String rawError) {
        if (rawError == null || rawError.isEmpty()) return "Unknown Error";

        String lowerError = rawError.toLowerCase();

        if (lowerError.contains("connection refused") || lowerError.contains("connectexception")) {
            return "Connection Error: Is the local AI (Ollama) running?";
        }

        if (lowerError.contains("read timed out") || lowerError.contains("sockettimeoutexception")) {
            return "Timeout: The AI took too long to respond. Try a smaller input.";
        }

        if (lowerError.contains("429") || lowerError.contains("quota exceeded") || lowerError.contains("too many requests")) {
            String waitTime = "";
            java.util.regex.Matcher m = java.util.regex.Pattern.compile("in ([0-9.]+[sm])").matcher(lowerError);
            if (m.find()) {
                waitTime = " (Wait: " + m.group(1) + ")";
            }
            return "Rate Limit Exceeded: Quota finished or too many requests." + waitTime;
        }


        if (lowerError.contains("not found") || lowerError.contains("404") || lowerError.contains("does not exist")) {
            return "Model Not Found: Check if the model name is correct and installed.";
        }

        if (lowerError.contains("context length") || lowerError.contains("token limit") || lowerError.contains("max tokens")) {
            return "Context Limit Exceeded: The input code snippet is too large for this model.";
        }

        if (lowerError.contains("401") || lowerError.contains("403") || lowerError.contains("unauthorized") || lowerError.contains("permission denied")) {
            return "Authorization Error: Invalid API Key or missing permissions.";
        }

        java.util.regex.Pattern p = java.util.regex.Pattern.compile("\"(message|error)\"\\s*:\\s*\"(.*?)\"");
        java.util.regex.Matcher m = p.matcher(rawError);

        if (m.find()) {
            String extractedMsg = m.group(2);
            extractedMsg = extractedMsg.replace("\\n", " ").replaceAll("https?://\\S+", "");

            return extractedMsg.length() > 200 ? extractedMsg.substring(0, 197) + "..." : extractedMsg;
        }

        return rawError.length() > 200 ? rawError.substring(0, 197) + "..." : rawError;
    }

    public static AnalysisMetadata extractGlobalMetadataFromFileResults(List<ObjectNode> results) {
        int totalOutputLength = 0;
        int totalIssuesCount = 0;

        for (ObjectNode snippetNode : results) {
            ArrayNode resArray = (ArrayNode) snippetNode.path("meta").path("res");

            for (JsonNode categoryNode : resArray) {
                totalOutputLength += categoryNode.path("output_length").asInt();

                ObjectNode response = (ObjectNode) categoryNode.path("response");
                totalIssuesCount += response.withArray("issues").size();
            }
        }

        byte globalSeverity = calculateFileGlobalSeverity(results);

        return new AnalysisMetadata(
                globalSeverity,
                totalOutputLength,
                totalIssuesCount
        );
    }

    public static HashSet<String> extractFoundIssuesFromResults(ObjectNode results) {
        HashSet<String> issuesToReturn = new HashSet<>();

        JsonNode response = results.path("response");
        if (!response.isObject()) return issuesToReturn;

        JsonNode issues = response.path("issues");
        if (!issues.isArray()) return issuesToReturn;

        for (JsonNode issue : issues) {
            String location = issue.path("location").asText("");
            String type = issue.path("type").asText("");

            if (!location.isEmpty() && !type.isEmpty()) {
                issuesToReturn.add(String.format("%s in %s", type, location));
            }
        }

        return issuesToReturn;
    }

    private static boolean isDuplicate(HashSet<String> foundIssues, String signatureToCompare) {
        for (String issueToCompare : foundIssues) {
            if (CodeManagement.getStringSimilarityPerc(issueToCompare, signatureToCompare) >= 0.50) {
                return true;
            }
        }
        return false;
    }

    public static ObjectNode removeDuplicateIssuesFromResult(ObjectNode results, HashSet<String> prevFoundIssues) {

        JsonNode responseNode = results.path("response");
        if (!responseNode.isObject()) return results;

        JsonNode issuesNode = responseNode.path("issues");
        if (!issuesNode.isArray()) return results;

        ArrayNode originalIssues = (ArrayNode) issuesNode;
        ArrayNode filteredIssues = OBJECT_MAPPER.createArrayNode();

        for (JsonNode issue : originalIssues) {
            String location = issue.path("location").asText("");
            String type = issue.path("type").asText("");

            if (location.isEmpty() || type.isEmpty()) {
                filteredIssues.add(issue);
                continue;
            }

            String signature = String.format("%s in %s", type, location);

            if (!isDuplicate(prevFoundIssues, signature)) {
                filteredIssues.add(issue);
                log.info("New error: {}", signature);
            } else {
                log.info("Duplicate error: {}", signature);
            }

        }

        if (responseNode instanceof ObjectNode) {
            ((ObjectNode) responseNode).set("issues", filteredIssues);
        }

        return results;
    }

    public static List<ObjectNode> aggregateByCategory(List<ObjectNode> fileAnalysisResults) {

        Map<String, ObjectNode> categoryMap = new HashMap<>();

        for (ObjectNode snippetNode : fileAnalysisResults) {
            JsonNode resArray = snippetNode.path("meta").path("res");

            if (resArray.isArray()) {
                for (JsonNode analysis : resArray) {

                    String category = analysis.path("meta").path("category").asText("UNKNOWN");

                    if (!categoryMap.containsKey(category)) {
                        categoryMap.put(category, analysis.deepCopy());
                    }
                    else {
                        ObjectNode masterNode = categoryMap.get(category);
                        ObjectNode masterMeta = (ObjectNode) masterNode.path("meta");
                        ObjectNode masterResponse = (ObjectNode) masterNode.path("response");

                        JsonNode currentMeta = analysis.path("meta");
                        JsonNode currentResponse = analysis.path("response");

                        long currentExec = currentMeta.path("execution_ms").asLong(0);
                        long totalExec = masterMeta.path("execution_ms").asLong(0);
                        masterMeta.put("execution_ms", totalExec + currentExec);

                        long currentInputLen = currentMeta.path("input_length").asLong(0);
                        long totalInputLen = masterMeta.path("input_length").asLong(0);
                        masterMeta.put("input_length", totalInputLen + currentInputLen);

                        String currentStart = currentMeta.path("start_datetime").asText();
                        String masterStart = masterMeta.path("start_datetime").asText();
                        if (currentStart != null && !currentStart.isEmpty() && (masterStart == null || currentStart.compareTo(masterStart) < 0)) {
                            masterMeta.put("start_datetime", currentStart);
                        }

                        String currentEnd = currentMeta.path("end_datetime").asText();
                        String masterEnd = masterMeta.path("end_datetime").asText();
                        if (currentEnd != null && !currentEnd.isEmpty() && (masterEnd == null || currentEnd.compareTo(masterEnd) > 0)) {
                            masterMeta.put("end_datetime", currentEnd);
                        }

                        int currentSev = currentResponse.path("overall_severity").asInt(0);
                        int masterSev = masterResponse.path("overall_severity").asInt(0);
                        if (currentSev > masterSev) {
                            masterResponse.put("overall_severity", currentSev);
                        }

                        ArrayNode masterIssuesArr = (ArrayNode) masterResponse.path("issues");
                        JsonNode currentIssues = currentResponse.path("issues");

                        if (currentIssues.isArray()) {
                            masterIssuesArr.addAll((ArrayNode) currentIssues);
                        }
                    }
                }
            }
        }

        return new ArrayList<>(categoryMap.values());
    }

    private ObjectNode getFinalOutput(List<ObjectNode> results, AiProviderConfig config) {
        ObjectNode finalOutput = OBJECT_MAPPER.createObjectNode();

        if (config instanceof OllamaSpecificConfig ollamaConfig) {
            finalOutput.set("configuration", OBJECT_MAPPER.valueToTree(ollamaConfig.getCustomConfigDto()));
        } else {
            finalOutput.putNull("configuration");
        }

        finalOutput.set("results", OBJECT_MAPPER.valueToTree(results));

        return finalOutput;
    }

}
