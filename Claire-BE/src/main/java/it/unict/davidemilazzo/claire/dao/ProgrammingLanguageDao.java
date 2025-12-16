package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.dto.ProgrammingLanguageDto;

import java.util.List;

public interface ProgrammingLanguageDao {
    ProgrammingLanguageDto createNew(ProgrammingLanguageDto programmingLanguageDto);
    ProgrammingLanguageDto update(ProgrammingLanguageDto programmingLanguageDto);
    ProgrammingLanguageDto delete(Long id);
    ProgrammingLanguageDto findById(Long id);
    ProgrammingLanguageDto findByName(String name);
    List<ProgrammingLanguageDto> findByNameContainingIgnoreCase(String name);
    List<ProgrammingLanguageDto> findAll();
    boolean exists(Long id);
    boolean existsByName(String name);
}
