package it.unict.davidemilazzo.claire.service;

import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import it.unict.davidemilazzo.claire.dao.AIDao;
import it.unict.davidemilazzo.claire.dto.AIDto;
import it.unict.davidemilazzo.claire.dto.AIPreviewDto;
import it.unict.davidemilazzo.claire.model.AIType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AIService {

    private final AIDao aiDao;

    public AIDto createNew(AIDto aiDto) {
        return aiDao.createNew(aiDto);
    }

    public AIDto update(AIDto aiDto) {
        return aiDao.update(aiDto);
    }

    public AIDto setActive(Long id, boolean status) {
        return aiDao.setActive(id, status);
    }

    public List<AIDto> getAllRegistered() {
        return aiDao.getAll();
    }

    public void disableAll() {
        aiDao.disableAll();
    }

    public List<AIDto> getAllEnabled() {
        return aiDao.findByActive(true);
    }

    public AIDto findById(Long id) {
        return aiDao.findById(id);
    }

    public AIDto findByName(String name) {
        return aiDao.findByName(name);
    }

    public AIDto findByModel(String model) {
        return aiDao.findByModel(model);
    }

    public List<AIDto> findByAiProviderName(AIProvidersEnum name) {
        return aiDao.findByAiProviderName(name);
    }

    public boolean exists(Long id) {
        return aiDao.exists(id);
    }

    public boolean existsByName(String name) {
        return aiDao.existsByName(name);
    }

    public boolean existsByModel(String model) {
        return aiDao.existsByModel(model);
    }

    public AIPreviewDto findAIPreviewById(Long aiId) {
        return aiDao.findAIPreviewById(aiId);
    }

    public List<AIPreviewDto> findAllAvailablePreviewForUser(Long userId) {
        return aiDao.findAllAvailablePreviewForUser(userId);
    }
}
