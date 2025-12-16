package it.unict.davidemilazzo.claire.service;

import it.unict.davidemilazzo.claire.dao.UserProviderApiKeyDao;
import it.unict.davidemilazzo.claire.dto.UserProviderApiKeyDto;
import it.unict.davidemilazzo.claire.dto.UserProviderApiKeyPreviewDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProviderApiKeyService {

    private final UserProviderApiKeyDao userProviderApiKeyDao;
    private final ApiKeyEncryptionService apiKeyEncryptionService;

    @Transactional
    public UserProviderApiKeyDto createNew(UserProviderApiKeyDto userProviderApiKeyDto) {
        userProviderApiKeyDto.setApiKey(apiKeyEncryptionService.encrypt(userProviderApiKeyDto.getApiKey()));
        return userProviderApiKeyDao.createNew(userProviderApiKeyDto);
    }

    @Transactional
    public UserProviderApiKeyDto update(UserProviderApiKeyDto userProviderApiKeyDto) {
        userProviderApiKeyDto.setApiKey(apiKeyEncryptionService.encrypt(userProviderApiKeyDto.getApiKey()));
        return userProviderApiKeyDao.update(userProviderApiKeyDto);
    }

    @Transactional
    public UserProviderApiKeyDto delete(Long id) {
        return userProviderApiKeyDao.delete(id);
    }

    public UserProviderApiKeyDto findById(Long id) {
        return userProviderApiKeyDao.findById(id);
    }

    public List<UserProviderApiKeyDto> findByUserId(Long id) {
        return userProviderApiKeyDao.findByUserId(id);
    }

    public UserProviderApiKeyDto findByProviderId(Long id) {
        return userProviderApiKeyDao.findByProviderId(id);
    }

    public UserProviderApiKeyDto findByUserAndProviderId(Long userId, Long providerId) {
        return userProviderApiKeyDao.findByUserAndProviderId(userId, providerId);
    }

    public UserProviderApiKeyDto findByUserAndProviderIdDecrypted(Long userId, Long providerId) {
        UserProviderApiKeyDto userProviderApiKeyDto = userProviderApiKeyDao.findByUserAndProviderId(userId, providerId);
        String decrypted = apiKeyEncryptionService.decrypt(userProviderApiKeyDto.getApiKey());
        userProviderApiKeyDto.setApiKey(decrypted);
        return userProviderApiKeyDto;
    }

    public List<UserProviderApiKeyPreviewDto> findPreviewByUserId(Long id) {
        return userProviderApiKeyDao.findPreviewByUserId(id);
    }

    public UserProviderApiKeyPreviewDto findPreviewByUserIdAndProviderId(Long userId, Long providerId) {
        return userProviderApiKeyDao.findPreviewByUserIdAndProviderId(userId, providerId);
    }

    public List<UserProviderApiKeyPreviewDto> findProvidersWithUserKeys(Long userId) {
        return userProviderApiKeyDao.findProvidersWithUserKeys(userId);
    }

    public List<UserProviderApiKeyPreviewDto> findProvidersWithUserKeysDecrypted(Long userId) {
        List<UserProviderApiKeyPreviewDto> userProviderApiKeyPreviewDtos = userProviderApiKeyDao.findProvidersWithUserKeys(userId);

        userProviderApiKeyPreviewDtos.forEach(dto -> {
            if (dto.getApiKey() != null) {
                String decrypted = apiKeyEncryptionService.decrypt(dto.getApiKey());
                dto.setApiKey(decrypted);
            }
        });

        return userProviderApiKeyPreviewDtos;
    }

    public boolean exists(Long id) {
        return userProviderApiKeyDao.exists(id);
    }

    public boolean existsByUserIdAndProviderId(Long userId, Long providerId) {
        return userProviderApiKeyDao.existsByUserIdAndProviderId(userId, providerId);
    }
}
