package it.unict.davidemilazzo.claire.respository;

import it.unict.davidemilazzo.claire.model.ProgrammingLanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgrammingLanguageRepository extends JpaRepository<ProgrammingLanguageEntity, Long> {
    ProgrammingLanguageEntity findByName(String name);
    boolean existsByName(String name);
    List<ProgrammingLanguageEntity> findByNameContainingIgnoreCase(String namePart);
}
