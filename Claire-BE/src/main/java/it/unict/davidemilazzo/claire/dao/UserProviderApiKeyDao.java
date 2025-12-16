package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.dto.UserProviderApiKeyDto;
import it.unict.davidemilazzo.claire.dto.UserProviderApiKeyPreviewDto;

import java.util.List;

public interface UserProviderApiKeyDao {
    UserProviderApiKeyDto createNew(UserProviderApiKeyDto userProviderApiKeyDto);
    UserProviderApiKeyDto update(UserProviderApiKeyDto userProviderApiKeyDto);
    UserProviderApiKeyDto delete(Long id);
    UserProviderApiKeyDto findById(Long id);
    List<UserProviderApiKeyDto> findByUserId(Long id);
    UserProviderApiKeyDto findByProviderId(Long id);
    List<UserProviderApiKeyPreviewDto> findPreviewByUserId(Long id);
    UserProviderApiKeyPreviewDto findPreviewByUserIdAndProviderId(Long userId, Long providerId);
    UserProviderApiKeyDto findByUserAndProviderId(Long userId, Long providerId);
    Boolean existsByUserIdAndProviderId(Long userId, Long providerId);
    List<UserProviderApiKeyPreviewDto> findProvidersWithUserKeys(Long userId);
    boolean exists(Long id);
}
