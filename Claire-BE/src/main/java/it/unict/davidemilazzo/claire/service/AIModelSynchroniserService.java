package it.unict.davidemilazzo.claire.service;

import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AIModelSynchroniserService {

    private final Map<AIProvidersEnum, ModelSyncInt> syncMap = new HashMap<>();

    @Autowired
    public AIModelSynchroniserService(List<ModelSyncInt> syncServices) {
        for (ModelSyncInt service : syncServices) {
            syncMap.put(service.provider(), service);
        }
    }

    public void syncProvider(AIProvidersEnum provider, String apiKey) {
        ModelSyncInt service = syncMap.get(provider);
        if (service == null) {
            throw new IllegalArgumentException("No sync service registered for provider: " + provider);
        }
        service.syncModelsWithDatabase(apiKey);
    }

    public void syncAll(Map<AIProvidersEnum, String> apiKeys) {
        for (Map.Entry<AIProvidersEnum, ModelSyncInt> entry : syncMap.entrySet()) {
            AIProvidersEnum provider = entry.getKey();
            ModelSyncInt service = entry.getValue();

            String apiKey = apiKeys.get(provider);
            if (provider.isLocal() || (apiKey != null && !apiKey.isBlank())) {
                service.syncModelsWithDatabase(apiKey);
            }
        }
    }
}

