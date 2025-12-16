package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.ai.ollama.UserOllamaCustomConfigDto;

public interface UserOllamaCustomConfigDao {
    UserOllamaCustomConfigDto createNew(UserOllamaCustomConfigDto customConfigDto);
    UserOllamaCustomConfigDto update(UserOllamaCustomConfigDto customConfigDto);
    UserOllamaCustomConfigDto findById(Long configId);
    UserOllamaCustomConfigDto delete(Long configId);
    boolean exists(Long configId);

    UserOllamaCustomConfigDto findByUserId(Long userId);
    boolean existsByUserId(Long userId);
    UserOllamaCustomConfigDto deleteByUserId(Long userId);
}
