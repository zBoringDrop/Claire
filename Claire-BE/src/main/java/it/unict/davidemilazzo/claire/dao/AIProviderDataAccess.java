package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import it.unict.davidemilazzo.claire.dto.AIProviderDto;
import it.unict.davidemilazzo.claire.exception.ExceptionMessages;
import it.unict.davidemilazzo.claire.exception.IdNotFoundException;
import it.unict.davidemilazzo.claire.mapper.AIProviderMapper;
import it.unict.davidemilazzo.claire.model.AIProviderEntity;
import it.unict.davidemilazzo.claire.respository.AIProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("aiprovider-mysql")
@RequiredArgsConstructor
public class AIProviderDataAccess implements AIProviderDao {

    private final AIProviderRepository aiProviderRepository;

    @Override
    public AIProviderDto createNew(AIProviderDto aiProviderDto) {
        AIProviderEntity aiProviderEntity = AIProviderMapper.MAPPER.toEntity(aiProviderDto);
        return AIProviderMapper.MAPPER.toDto(aiProviderRepository.save(aiProviderEntity));
    }

    @Override
    public AIProviderDto update(AIProviderDto aiProviderDto) {
        AIProviderEntity aiProviderEntity = aiProviderRepository.findById(aiProviderDto.getId())
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.AI_PROVIDER_NOT_FOUND));

        aiProviderEntity.setName(aiProviderEntity.getName());
        aiProviderEntity.setDescription(aiProviderEntity.getDescription());
        aiProviderEntity.setAiType(aiProviderEntity.getAiType());
        aiProviderEntity.setBaseUrl(aiProviderEntity.getBaseUrl());

        return AIProviderMapper.MAPPER.toDto(aiProviderRepository.save(aiProviderEntity));
    }

    @Override
    public AIProviderDto delete(Long id) {
        AIProviderEntity aiProviderEntity = aiProviderRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));

        AIProviderDto aiProviderDto = AIProviderMapper.MAPPER.toDto(aiProviderEntity);
        aiProviderRepository.deleteById(aiProviderEntity.getId());
        return aiProviderDto;
    }

    @Override
    public AIProviderDto findById(Long id) {
        AIProviderEntity aiProviderEntity = aiProviderRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));

        return AIProviderMapper.MAPPER.toDto(aiProviderEntity);
    }

    @Override
    public AIProviderDto findByName(AIProvidersEnum name) {
        AIProviderEntity aiProviderEntity = aiProviderRepository.findByName(name);
        return AIProviderMapper.MAPPER.toDto(aiProviderEntity);
    }

    @Override
    public List<AIProviderDto> findAll() {
        return AIProviderMapper.MAPPER.toDtoList(aiProviderRepository.findAll());
    }

    @Override
    public boolean exists(Long id) {
        return aiProviderRepository.existsById(id);
    }
}
