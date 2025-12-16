package it.unict.davidemilazzo.claire.service;

import it.unict.davidemilazzo.claire.dao.StaticToolDao;
import it.unict.davidemilazzo.claire.dto.StaticToolDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StaticToolService {
    private final StaticToolDao staticToolDao;

    public StaticToolDto createNew(StaticToolDto staticToolDto) {
        return staticToolDao.createNew(staticToolDto);
    }

    public StaticToolDto update(StaticToolDto staticToolDto) {
        return staticToolDao.update(staticToolDto);
    }

    public StaticToolDto findById(Long id) {
        return staticToolDao.findById(id);
    }

    public StaticToolDto findByName(String name) {
        return staticToolDao.findByName(name);
    }

    public List<StaticToolDto> getAll() {
        return staticToolDao.getAll();
    }

    public StaticToolDto delete(Long id) {
        return staticToolDao.delete(id);
    }

    public boolean exists(Long id) {
        return staticToolDao.exists(id);
    }

    public boolean existsByName(String name) {
        return staticToolDao.existsByName(name);
    }

    public List<StaticToolDto> findByVersion(String version) {
        return staticToolDao.findByVersion(version);
    }
}
