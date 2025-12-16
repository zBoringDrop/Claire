package it.unict.davidemilazzo.claire.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulesService {

    private final OllamaService ollamaService;
    private final GeminiService geminiService;
    private final AIService aiService;
    private static final Logger log = LogManager.getLogger(SchedulesService.class);

    @Scheduled(fixedDelay = 600000, initialDelay = 1000)
    public void modelsSynchronisation() {
        aiService.disableAll();

        log.info("Starting automatic synchronisation of Ollama models in the database...");
            ollamaService.syncModelsWithDatabase(null);
        log.info("Automatic Ollama models synchronisation completed");

        /*
        log.info("Starting automatic synchronisation of Gemini models in the database...");
            geminiService.syncModelsWithDatabase();
        log.info("Automatic Gemini models synchronisation completed");
         */
    }

}
