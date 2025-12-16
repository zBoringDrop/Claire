package it.unict.davidemilazzo.claire.respository;

import it.unict.davidemilazzo.claire.dto.UserProviderApiKeyPreviewDto;
import it.unict.davidemilazzo.claire.model.UserProviderApiKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserProviderApiKeyRepository extends JpaRepository<UserProviderApiKeyEntity, Long> {

    List<UserProviderApiKeyEntity> findByUser_Id(Long id);
    UserProviderApiKeyEntity findByProvider_Id(Long providerId);
    UserProviderApiKeyEntity findByUser_IdAndProvider_Id(Long userId, Long providerId);
    Boolean existsByUser_IdAndProvider_Id(Long userId, Long providerId);

    @Query("""
           SELECT new it.unict.davidemilazzo.claire.dto.UserProviderApiKeyPreviewDto(
               upa.id,
               upa.user.id,
               upa.provider.id,
               upa.provider.name,
               upa.provider.aiType,
               upa.provider.baseUrl,
               upa.provider.description,
               upa.apiKey,
               upa.active
           )
           FROM UserProviderApiKeyEntity upa
           WHERE upa.user.id = :userId
           """)
    List<UserProviderApiKeyPreviewDto> findPreviewByUserId(@Param("userId") Long userId);

    @Query("""
           SELECT new it.unict.davidemilazzo.claire.dto.UserProviderApiKeyPreviewDto(
               upa.id,
               upa.user.id,
               upa.provider.id,
               upa.provider.name,
               upa.provider.aiType,
               upa.provider.baseUrl,
               upa.provider.description,
               upa.apiKey,
               upa.active
           )
           FROM UserProviderApiKeyEntity upa
           WHERE upa.user.id = :userId
             AND upa.provider.id = :providerId
           """)
    UserProviderApiKeyPreviewDto findPreviewByUserIdAndProviderId(
            @Param("userId") Long userId,
            @Param("providerId") Long providerId);

    @Query("""
            SELECT new it.unict.davidemilazzo.claire.dto.UserProviderApiKeyPreviewDto(
                k.id,
                :userId,
                p.id,
                p.name,
                p.aiType,
                p.baseUrl,
                p.description,
                k.apiKey,
                k.active
            )
            FROM AIProviderEntity p
            LEFT JOIN UserProviderApiKeyEntity k 
                ON k.provider.id = p.id AND k.user.id = :userId
        """)
    List<UserProviderApiKeyPreviewDto> findProvidersWithUserKeys(@Param("userId") Long userId);


}

