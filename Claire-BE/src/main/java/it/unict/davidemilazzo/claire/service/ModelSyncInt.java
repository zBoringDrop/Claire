package it.unict.davidemilazzo.claire.service;

import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;

public interface ModelSyncInt {
    AIProvidersEnum provider();
    void syncModelsWithDatabase(final String apiKey);
}
