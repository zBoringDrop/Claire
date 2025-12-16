package it.unict.davidemilazzo.claire.respository;

import it.unict.davidemilazzo.claire.model.StaticToolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StaticToolRepository extends JpaRepository<StaticToolEntity, Long> {
    Optional<StaticToolEntity> findByName(String name);
    boolean existsByName(String name);
    List<StaticToolEntity> findByVersion(String version);
}
