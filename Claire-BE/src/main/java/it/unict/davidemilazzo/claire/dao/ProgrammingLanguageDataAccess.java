package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.dto.ProgrammingLanguageDto;
import it.unict.davidemilazzo.claire.exception.ExceptionMessages;
import it.unict.davidemilazzo.claire.exception.IdNotFoundException;
import it.unict.davidemilazzo.claire.mapper.ProgrammingLanguageMapper;
import it.unict.davidemilazzo.claire.model.ProgrammingLanguageEntity;
import it.unict.davidemilazzo.claire.respository.ProgrammingLanguageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository("programminglanguage-mysql")
public class ProgrammingLanguageDataAccess implements ProgrammingLanguageDao {

    private final ProgrammingLanguageRepository programmingLanguageRepository;

    @Override
    public ProgrammingLanguageDto createNew(ProgrammingLanguageDto programmingLanguageDto) {
        ProgrammingLanguageEntity programmingLanguageEntity
                = programmingLanguageRepository.save(ProgrammingLanguageMapper.MAPPER.toEntity(programmingLanguageDto));

        return ProgrammingLanguageMapper.MAPPER.toDto(programmingLanguageEntity);
    }

    @Override
    public ProgrammingLanguageDto update(ProgrammingLanguageDto programmingLanguageDto) {
        ProgrammingLanguageEntity programmingLanguageEntity
                = programmingLanguageRepository.findById(programmingLanguageDto.getId())
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));

        programmingLanguageEntity.setName(programmingLanguageDto.getName());
        return ProgrammingLanguageMapper.MAPPER.toDto(programmingLanguageRepository.save(programmingLanguageEntity));
    }

    @Override
    public ProgrammingLanguageDto delete(Long id) {
        ProgrammingLanguageEntity programmingLanguageEntity
                = programmingLanguageRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));

        ProgrammingLanguageDto programmingLanguageDto = ProgrammingLanguageMapper.MAPPER.toDto(programmingLanguageEntity);
        programmingLanguageRepository.deleteById(id);
        return programmingLanguageDto;
    }

    @Override
    public ProgrammingLanguageDto findById(Long id) {
        ProgrammingLanguageEntity programmingLanguageEntity =
                programmingLanguageRepository.findById(id)
                        .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        return ProgrammingLanguageMapper.MAPPER.toDto(programmingLanguageEntity);
    }

    @Override
    public ProgrammingLanguageDto findByName(String name) {
        return ProgrammingLanguageMapper.MAPPER.toDto(programmingLanguageRepository.findByName(name));
    }

    @Override
    public List<ProgrammingLanguageDto> findByNameContainingIgnoreCase(String name) {
        return ProgrammingLanguageMapper.MAPPER.toDtoList(programmingLanguageRepository.findByNameContainingIgnoreCase(name));
    }

    @Override
    public List<ProgrammingLanguageDto> findAll() {
        return ProgrammingLanguageMapper.MAPPER.toDtoList(programmingLanguageRepository.findAll());
    }

    @Override
    public boolean exists(Long id) {
        return programmingLanguageRepository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return programmingLanguageRepository.existsByName(name);
    }
}
