package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.dto.UserProviderApiKeyDto;
import it.unict.davidemilazzo.claire.dto.UserProviderApiKeyPreviewDto;
import it.unict.davidemilazzo.claire.exception.ExceptionMessages;
import it.unict.davidemilazzo.claire.exception.IdNotFoundException;
import it.unict.davidemilazzo.claire.mapper.UserProviderApiKeyMapper;
import it.unict.davidemilazzo.claire.model.AIProviderEntity;
import it.unict.davidemilazzo.claire.model.UserProviderApiKeyEntity;
import it.unict.davidemilazzo.claire.respository.UserProviderApiKeyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userprovider-mysql")
@RequiredArgsConstructor
public class UserProviderApiKeyDataAccess implements UserProviderApiKeyDao {

    private final UserProviderApiKeyRepository userProviderApiKeyRepository;

    @Override
    public UserProviderApiKeyDto createNew(UserProviderApiKeyDto userProviderApiKeyDto) {
        UserProviderApiKeyEntity userProviderApiKeyEntity = UserProviderApiKeyMapper.INSTANCE.toEntity(userProviderApiKeyDto);
        return UserProviderApiKeyMapper.INSTANCE.toDto(userProviderApiKeyRepository.save(userProviderApiKeyEntity));
    }

    @Override
    public UserProviderApiKeyDto update(UserProviderApiKeyDto dto) {

        UserProviderApiKeyEntity entity = userProviderApiKeyRepository.findById(dto.getId())
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));

        if (dto.getProviderId() != null) {
            AIProviderEntity provider = AIProviderEntity.builder()
                    .id(dto.getProviderId())
                    .build();
            entity.setProvider(provider);
        }

        if (dto.getApiKey() != null) {
            entity.setApiKey(dto.getApiKey());
        }

        entity.setActive(dto.getActive());

        entity = userProviderApiKeyRepository.save(entity);

        return UserProviderApiKeyMapper.INSTANCE.toDto(entity);
    }

    @Override
    public UserProviderApiKeyDto delete(Long id) {
        UserProviderApiKeyEntity entity = userProviderApiKeyRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));

        UserProviderApiKeyDto userProviderApiKeyDto = UserProviderApiKeyMapper.INSTANCE.toDto(entity);
        userProviderApiKeyRepository.deleteById(id);

        return userProviderApiKeyDto;
    }

    @Override
    public UserProviderApiKeyDto findById(Long id) {
        UserProviderApiKeyEntity entity = userProviderApiKeyRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));

        return UserProviderApiKeyMapper.INSTANCE.toDto(entity);
    }

    @Override
    public List<UserProviderApiKeyDto> findByUserId(Long id) {
        List<UserProviderApiKeyEntity> entityList = userProviderApiKeyRepository.findByUser_Id(id);

        return UserProviderApiKeyMapper.INSTANCE.toDtoList(entityList);
    }

    @Override
    public UserProviderApiKeyDto findByProviderId(Long id) {
        UserProviderApiKeyEntity entity = userProviderApiKeyRepository.findByProvider_Id(id);

        return UserProviderApiKeyMapper.INSTANCE.toDto(entity);
    }

    @Override
    public List<UserProviderApiKeyPreviewDto> findPreviewByUserId(Long id) {
        return userProviderApiKeyRepository.findPreviewByUserId(id);
    }

    @Override
    public UserProviderApiKeyPreviewDto findPreviewByUserIdAndProviderId(Long userId, Long providerId) {
        return userProviderApiKeyRepository.findPreviewByUserIdAndProviderId(userId, providerId);
    }

    @Override
    public UserProviderApiKeyDto findByUserAndProviderId(Long userId, Long providerId) {
        UserProviderApiKeyEntity entity = userProviderApiKeyRepository.findByUser_IdAndProvider_Id(userId, providerId);

        return UserProviderApiKeyMapper.INSTANCE.toDto(entity);
    }

    @Override
    public Boolean existsByUserIdAndProviderId(Long userId, Long providerId) {
        return userProviderApiKeyRepository.existsByUser_IdAndProvider_Id(userId, providerId);
    }

    @Override
    public List<UserProviderApiKeyPreviewDto> findProvidersWithUserKeys(Long userId) {
        return userProviderApiKeyRepository.findProvidersWithUserKeys(userId);
    }

    @Override
    public boolean exists(Long id) {
        return userProviderApiKeyRepository.existsById(id);
    }
}
