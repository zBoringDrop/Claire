package it.unict.davidemilazzo.claire.service;

import it.unict.davidemilazzo.claire.ai.ollama.UserOllamaCustomConfigDto;
import it.unict.davidemilazzo.claire.dao.UserOllamaCustomConfigDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOllamaCustomConfigService {

    private final UserOllamaCustomConfigDao userOllamaCustomConfigDao;

    public UserOllamaCustomConfigDto createNew(UserOllamaCustomConfigDto customConfigDto, Long userId) {
        customConfigDto.setId(null);
        customConfigDto.setUserId(userId);
        return userOllamaCustomConfigDao.createNew(customConfigDto);
    }

    public UserOllamaCustomConfigDto update(UserOllamaCustomConfigDto customConfigDto, Long userId) {
        Long userConfigId = findByUserId(userId).getId();
        customConfigDto.setId(userConfigId);
        customConfigDto.setUserId(userId);
        return userOllamaCustomConfigDao.update(customConfigDto);
    }

    public UserOllamaCustomConfigDto findById(Long configId) {
        return userOllamaCustomConfigDao.findById(configId);
    }

    public UserOllamaCustomConfigDto delete(Long configId) {
        return userOllamaCustomConfigDao.delete(configId);
    }

    public boolean exists(Long configId) {
        return userOllamaCustomConfigDao.exists(configId);
    }

    public UserOllamaCustomConfigDto findByUserId(Long userId) {
        return userOllamaCustomConfigDao.findByUserId(userId);
    }

    public boolean existsByUserId(Long userId) {
        return userOllamaCustomConfigDao.existsByUserId(userId);
    }

    public UserOllamaCustomConfigDto deleteByUserId(Long userId) {
        return userOllamaCustomConfigDao.deleteByUserId(userId);
    }
}
