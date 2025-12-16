package it.unict.davidemilazzo.claire.service;

import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import it.unict.davidemilazzo.claire.dao.AIProviderDao;
import it.unict.davidemilazzo.claire.dto.AIProviderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AIProviderService {

    private final AIProviderDao aiProviderDao;

    public AIProviderDto createNew(AIProviderDto aiProviderDto) {
        return aiProviderDao.createNew(aiProviderDto);
    }

    public AIProviderDto update(AIProviderDto aiProviderDto) {
        return aiProviderDao.update(aiProviderDto);
    }

    public AIProviderDto delete(Long id) {
        return aiProviderDao.delete(id);
    }

    public AIProviderDto findById(Long id) {
        return aiProviderDao.findById(id);
    }

    public AIProviderDto findByName(AIProvidersEnum name) {
        return aiProviderDao.findByName(name);
    }

    public List<AIProviderDto> findAll() {
        return aiProviderDao.findAll();
    }

    public boolean exists(Long id) {
        return aiProviderDao.exists(id);
    }
}
