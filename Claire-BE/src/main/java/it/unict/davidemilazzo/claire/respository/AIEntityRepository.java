package it.unict.davidemilazzo.claire.respository;

import it.unict.davidemilazzo.claire.ai.AIProvidersEnum;
import it.unict.davidemilazzo.claire.dto.AIPreviewDto;
import it.unict.davidemilazzo.claire.model.AIEntity;
import it.unict.davidemilazzo.claire.model.AIType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AIEntityRepository extends JpaRepository<AIEntity, Long> {
    Optional<AIEntity> findByName(String name);
    Optional<AIEntity> findByModel(String model);
    List<AIEntity> findByFamily(String name);
    List<AIEntity> findByParameterSize(String parameterSize);
    List<AIEntity> findBySize(long size);
    List<AIEntity> findByAiProviderEntity_Name(AIProvidersEnum providerName);
    List<AIEntity> findByActive(boolean active);
    boolean existsByName(String name);
    boolean existsByModel(String model);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
            UPDATE AIEntity a
            SET a.active = false
            WHERE a.aiProviderEntity.aiType = 'LOCAL'
        """)
    void disableLocalModels();


    @Query("""
        SELECT ai
        FROM AIEntity ai
        WHERE ai.aiProviderEntity.aiType = 'LOCAL'
           OR ai.aiProviderEntity.id IN (
                SELECT upa.provider.id
                FROM UserProviderApiKeyEntity upa
                WHERE upa.user.id = :userId
                  AND upa.active = true
           )
        """)
    List<AIEntity> findAllAvailableForUser(@Param("userId") Long userId);

    @Query("""
    SELECT new it.unict.davidemilazzo.claire.dto.AIPreviewDto(
        ai.id,
        ai.name,
        ai.model,
        ai.family,
        ai.parameterSize,
        ai.size,
        provider.id,
        ai.description,
        ai.active,
        ai.modifiedAt,
        ai.lastDBSync,
        ai.jsonExtra,
        provider.name,
        provider.baseUrl,
        provider.aiType,
        provider.description
    )
    FROM AIEntity ai
    JOIN ai.aiProviderEntity provider
    WHERE ai.id = :aiId
    """)
    AIPreviewDto findAIPreviewById(@Param("aiId") Long aiId);

    @Query("""
    SELECT new it.unict.davidemilazzo.claire.dto.AIPreviewDto(
        ai.id,
        ai.name,
        ai.model,
        ai.family,
        ai.parameterSize,
        ai.size,
        provider.id,
        ai.description,
        ai.active,
        ai.modifiedAt,
        ai.lastDBSync,
        ai.jsonExtra,
        provider.name,
        provider.baseUrl,
        provider.aiType,
        provider.description
    )
    FROM AIEntity ai
    JOIN ai.aiProviderEntity provider
    WHERE provider.aiType = 'LOCAL'
       OR provider.id IN (
            SELECT upa.provider.id
            FROM UserProviderApiKeyEntity upa
            WHERE upa.user.id = :userId
              AND upa.active = true
        )
""")
    List<AIPreviewDto> findAllAvailablePreviewForUser(@Param("userId") Long userId);

}
