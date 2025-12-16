package it.unict.davidemilazzo.claire.service;

import it.unict.davidemilazzo.claire.dao.ProgrammingLanguageDao;
import it.unict.davidemilazzo.claire.dto.ProgrammingLanguageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgrammingLanguageService {

    private final ProgrammingLanguageDao programmingLanguageDao;

    public ProgrammingLanguageDto createNew(ProgrammingLanguageDto programmingLanguageDto) {
        return programmingLanguageDao.createNew(programmingLanguageDto);
    }

    public ProgrammingLanguageDto update(ProgrammingLanguageDto programmingLanguageDto) {
        return programmingLanguageDao.update(programmingLanguageDto);
    }

    public ProgrammingLanguageDto delete(Long id) {
        return programmingLanguageDao.delete(id);
    }

    public ProgrammingLanguageDto findById(Long id) {
        return programmingLanguageDao.findById(id);
    }

    public ProgrammingLanguageDto findByName(String name) {
        return programmingLanguageDao.findByName(name);
    }

    public List<ProgrammingLanguageDto> findByNameContainingIgnoreCase(String name) {
        return programmingLanguageDao.findByNameContainingIgnoreCase(name);
    }

    public List<ProgrammingLanguageDto> findAll() { return programmingLanguageDao.findAll(); }

    public boolean exists(Long id) {
        return programmingLanguageDao.exists(id);
    }

    public boolean existsByName(String name) {
        return programmingLanguageDao.existsByName(name);
    }
}
