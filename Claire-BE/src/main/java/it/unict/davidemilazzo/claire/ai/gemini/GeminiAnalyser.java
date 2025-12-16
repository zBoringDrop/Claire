package it.unict.davidemilazzo.claire.ai.gemini;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unict.davidemilazzo.claire.ai.AnalyserInt;
import it.unict.davidemilazzo.claire.ai.AnalysisConfig;
import it.unict.davidemilazzo.claire.exception.ExceptionMessages;
import it.unict.davidemilazzo.claire.exception.JsonParseErrorException;
import it.unict.davidemilazzo.claire.exception.ModelNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.ResponseFormat;
import org.springframework.web.client.RestClientException;

@Slf4j
public class GeminiAnalyser implements AnalyserInt {

    private final OpenAiChatModel chatModel;

    public GeminiAnalyser(OpenAiChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @Override
    public JsonNode analyze(AnalysisConfig analysisConfig) {
        String promptStr = analysisConfig.getPrompt();
        String jsonSchema = analysisConfig.getResponseJson();

        Prompt prompt;
        try {
            OpenAiChatOptions geminiChatOptions = OpenAiChatOptions.builder()
                    .model(analysisConfig.getModel())
                    .responseFormat(new ResponseFormat(ResponseFormat.Type.JSON_SCHEMA, jsonSchema))
                    .temperature(0.1d)
                    .build();
            prompt = new Prompt(promptStr, geminiChatOptions);
        } catch (IllegalArgumentException e) {
            throw new ModelNotFoundException(ExceptionMessages.MODEL_NOT_FOUND);
        }

        try {
            ChatResponse response = chatModel.call(prompt);

            if (response == null || response.getResult() == null || response.getResult().getOutput() == null) {
                throw new RuntimeException("AI Model returned empty result (possible filter or error)");
            }

            String text = response.getResult().getOutput().getText();
            JsonNode resultNode = new ObjectMapper().readTree(text);

            if (resultNode.has("error") || resultNode.path("error").isObject()) {
                String errorMsg = resultNode.toPrettyString();
                throw new RuntimeException("AI Provider returned an error JSON: " + errorMsg);
            }

            return resultNode;

        } catch (JsonProcessingException e) {
            throw new JsonParseErrorException(ExceptionMessages.JSON_PARSE_ERROR);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error during AI analysis: " + e.getMessage(), e);
        }
    }
}