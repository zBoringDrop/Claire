package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import it.unict.davidemilazzo.claire.dto.AIDto;
import it.unict.davidemilazzo.claire.dto.AIPreviewDto;
import it.unict.davidemilazzo.claire.exception.ExceptionMessages;
import it.unict.davidemilazzo.claire.exception.IdNotFoundException;
import it.unict.davidemilazzo.claire.mapper.ToolMapper;
import it.unict.davidemilazzo.claire.model.AIEntity;
import it.unict.davidemilazzo.claire.model.AIProviderEntity;
import it.unict.davidemilazzo.claire.model.ToolType;
import it.unict.davidemilazzo.claire.respository.AIEntityRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository("ai-mysql")
public class AIDataAccess implements AIDao {

    private static final Logger log = LogManager.getLogger(AIDataAccess.class);
    private final AIEntityRepository aiEntityRepository;

    @Override
    public AIDto createNew(AIDto aiDto) {
        AIEntity aiEntity = ToolMapper.INSTANCE.toEntity(aiDto);
        aiEntity.setType(ToolType.AI);
        return ToolMapper.INSTANCE.toDto(aiEntityRepository.save(aiEntity));
    }

    @Override
    public AIDto update(AIDto aiDto) {
        AIEntity aiEntityToUpdate = aiEntityRepository.findById(aiDto.getId())
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));

        aiEntityToUpdate.setName(aiDto.getName());
        aiEntityToUpdate.setDescription(aiDto.getDescription());
        aiEntityToUpdate.setModel(aiDto.getModel());
        aiEntityToUpdate.setFamily(aiDto.getFamily());
        aiEntityToUpdate.setParameterSize(aiDto.getParameterSize());
        aiEntityToUpdate.setSize(aiDto.getSize());

        if (aiDto.getAiProviderId() != null) {
            AIProviderEntity provider = AIProviderEntity.builder()
                    .id(aiDto.getAiProviderId())
                    .build();
            aiEntityToUpdate.setAiProviderEntity(provider);
        }

        aiEntityToUpdate.setModifiedAt(aiDto.getModifiedAt());
        aiEntityToUpdate.setLastDBSync(aiDto.getLastDBSync());
        aiEntityToUpdate.setActive(aiDto.isActive());
        aiEntityToUpdate.setJsonExtra(aiDto.getJsonExtra());

        return ToolMapper.INSTANCE.toDto(aiEntityRepository.save(aiEntityToUpdate));
    }


    @Override
    public AIDto setActive(Long id, boolean status) {
        AIEntity aiEntity = aiEntityRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        aiEntity.setActive(status);
        aiEntityRepository.save(aiEntity);
        return ToolMapper.INSTANCE.toDto(aiEntity);
    }

    @Override
    public void disableAll() {
        aiEntityRepository.disableLocalModels();
    }

    @Override
    public AIDto findById(Long id) {
        AIEntity aiEntity = aiEntityRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        return ToolMapper.INSTANCE.toDto(aiEntity);
    }

    @Override
    public AIDto findByName(String name) {
        AIEntity aiEntity = aiEntityRepository.findByName(name)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        return ToolMapper.INSTANCE.toDto(aiEntity);
    }

    @Override
    public AIDto findByModel(String model) {
        AIEntity aiEntity = aiEntityRepository.findByModel(model)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        return ToolMapper.INSTANCE.toDto(aiEntity);
    }

    @Override
    public List<AIDto> findByFamily(String name) {
        return ToolMapper.INSTANCE.toDtoListAI(aiEntityRepository.findByFamily(name));
    }

    @Override
    public List<AIDto> findByParameterSize(String parameterSize) {
        return ToolMapper.INSTANCE.toDtoListAI(aiEntityRepository.findByParameterSize(parameterSize));
    }

    @Override
    public List<AIDto> findBySize(long size) {
        return ToolMapper.INSTANCE.toDtoListAI(aiEntityRepository.findBySize(size));
    }

    @Override
    public List<AIDto> findByAiProviderName(AIProvidersEnum name) {
        return ToolMapper.INSTANCE.toDtoListAI(aiEntityRepository.findByAiProviderEntity_Name(name));
    }

    @Override
    public List<AIDto> findByActive(boolean isActive) {
        return ToolMapper.INSTANCE.toDtoListAI(aiEntityRepository.findByActive(isActive));
    }

    @Override
    public AIPreviewDto findAIPreviewById(Long aiId) {
        return aiEntityRepository.findAIPreviewById(aiId);
    }

    @Override
    public List<AIPreviewDto> findAllAvailablePreviewForUser(Long userId) {
        return aiEntityRepository.findAllAvailablePreviewForUser(userId);
    }

    @Override
    public List<AIDto> getAll() {
        return ToolMapper.INSTANCE.toDtoListAI(aiEntityRepository.findAll());
    }

    @Override
    public AIDto delete(Long id) {
        AIEntity aiEntityToDelete = aiEntityRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        AIDto aiDto = ToolMapper.INSTANCE.toDto(aiEntityToDelete);
        aiEntityRepository.delete(aiEntityToDelete);
        return aiDto;
    }

    @Override
    public boolean exists(Long id) {
        return aiEntityRepository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return aiEntityRepository.existsByName(name);
    }

    @Override
    public boolean existsByModel(String model) {
        return aiEntityRepository.existsByModel(model);
    }
}
