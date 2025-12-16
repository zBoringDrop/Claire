package it.unict.davidemilazzo.claire.respository;

import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import it.unict.davidemilazzo.claire.model.AIProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AIProviderRepository extends JpaRepository<AIProviderEntity, Long> {
    AIProviderEntity findByName(AIProvidersEnum name);
}
