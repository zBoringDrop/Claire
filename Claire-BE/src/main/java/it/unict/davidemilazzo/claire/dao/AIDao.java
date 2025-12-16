package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import it.unict.davidemilazzo.claire.dto.AIDto;
import it.unict.davidemilazzo.claire.dto.AIPreviewDto;

import java.util.List;

public interface AIDao {
    AIDto createNew(AIDto aiDto);
    AIDto update(AIDto aiDto);
    AIDto setActive(Long id, boolean status);
    void disableAll();
    AIDto findById(Long id);
    AIDto findByName(String name);
    AIDto findByModel(String model);
    List<AIDto> findByFamily(String name);
    List<AIDto> findByParameterSize(String parameterSize);
    List<AIDto> findBySize(long size);
    List<AIDto> findByAiProviderName(AIProvidersEnum name);
    List<AIDto> findByActive(boolean isActive);
    AIPreviewDto findAIPreviewById(Long aiId);
    List<AIPreviewDto> findAllAvailablePreviewForUser(Long userId);
    List<AIDto> getAll();
    AIDto delete(Long id);
    boolean exists(Long id);
    boolean existsByName(String name);
    boolean existsByModel(String model);
}
