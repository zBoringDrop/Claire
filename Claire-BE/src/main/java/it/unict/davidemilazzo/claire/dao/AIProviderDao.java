package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import it.unict.davidemilazzo.claire.dto.AIProviderDto;

import java.util.List;

public interface AIProviderDao {
    AIProviderDto createNew(AIProviderDto aiProviderDto);
    AIProviderDto update(AIProviderDto aiProviderDto);
    AIProviderDto delete(Long id);
    AIProviderDto findById(Long id);
    AIProviderDto findByName(AIProvidersEnum name);
    List<AIProviderDto> findAll();
    boolean exists(Long id);
}
