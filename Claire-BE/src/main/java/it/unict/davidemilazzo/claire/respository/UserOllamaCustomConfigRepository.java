package it.unict.davidemilazzo.claire.respository;

import it.unict.davidemilazzo.claire.model.UserOllamaCustomConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserOllamaCustomConfigRepository extends JpaRepository<UserOllamaCustomConfigEntity, Long> {
    Optional<UserOllamaCustomConfigEntity> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    void deleteByUserId(Long userId);
}
