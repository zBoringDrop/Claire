package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.ai.ollama.UserOllamaCustomConfigDto;
import it.unict.davidemilazzo.claire.exception.ExceptionMessages;
import it.unict.davidemilazzo.claire.exception.IdNotFoundException;
import it.unict.davidemilazzo.claire.mapper.UserOllamaCustomConfigMapper;
import it.unict.davidemilazzo.claire.model.UserOllamaCustomConfigEntity;
import it.unict.davidemilazzo.claire.respository.UserOllamaCustomConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository("userollamacustomconfig-mysql")
@RequiredArgsConstructor
public class UserOllamaCustomConfigDataAccess implements UserOllamaCustomConfigDao {

    private final UserOllamaCustomConfigRepository configRepository;

    @Override
    public UserOllamaCustomConfigDto createNew(UserOllamaCustomConfigDto customConfigDto) {
        UserOllamaCustomConfigEntity configEntity = configRepository.save(UserOllamaCustomConfigMapper.INSTANCE.toEntity(customConfigDto));
        return UserOllamaCustomConfigMapper.INSTANCE.toDto(configEntity);
    }

    @Override
    public UserOllamaCustomConfigDto update(UserOllamaCustomConfigDto customConfigDto) {

        UserOllamaCustomConfigEntity configEntity = configRepository.findById(customConfigDto.getId())
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));

        if (customConfigDto.getUseCustomConfig() != null) {
            configEntity.setUseCustomConfig(customConfigDto.getUseCustomConfig());
        }

        configEntity.setTemperature(customConfigDto.getTemperature());
        configEntity.setTopK(customConfigDto.getTopK());
        configEntity.setTopP(customConfigDto.getTopP());
        configEntity.setRepeatPenalty(customConfigDto.getRepeatPenalty());

        configEntity.setNumCtx(customConfigDto.getNumCtx());
        configEntity.setNumPredict(customConfigDto.getNumPredict());
        configEntity.setNumThread(customConfigDto.getNumThread());
        configEntity.setKeepAlive(customConfigDto.getKeepAlive());

        configEntity.setNumGpu(customConfigDto.getNumGpu());
        configEntity.setMainGpu(customConfigDto.getMainGpu());
        configEntity.setUseMMap(customConfigDto.getUseMMap());

        configEntity.setSeed(customConfigDto.getSeed());

        return UserOllamaCustomConfigMapper.INSTANCE.toDto(configRepository.save(configEntity));
    }

    @Override
    public UserOllamaCustomConfigDto findById(Long configId) {
        return UserOllamaCustomConfigMapper.INSTANCE.toDto(configRepository.findById(configId)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND)));
    }

    @Override
    public UserOllamaCustomConfigDto delete(Long configId) {
        UserOllamaCustomConfigDto configDto = UserOllamaCustomConfigMapper.INSTANCE.toDto(configRepository.findById(configId)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND)));

        configRepository.deleteById(configId);

        return configDto;
    }

    @Override
    public boolean exists(Long configId) {
        return configRepository.existsById(configId);
    }

    @Override
    public UserOllamaCustomConfigDto findByUserId(Long userId) {
        UserOllamaCustomConfigEntity configEntity = configRepository.findByUserId(userId)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));

        return UserOllamaCustomConfigMapper.INSTANCE.toDto(configEntity);
    }

    @Override
    public boolean existsByUserId(Long userId) {
        return configRepository.existsByUserId(userId);
    }

    @Override
    public UserOllamaCustomConfigDto deleteByUserId(Long userId) {
        UserOllamaCustomConfigDto configDto = UserOllamaCustomConfigMapper.INSTANCE.toDto(configRepository.findByUserId(userId)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND)));

        configRepository.deleteByUserId(userId);

        return configDto;
    }
}
