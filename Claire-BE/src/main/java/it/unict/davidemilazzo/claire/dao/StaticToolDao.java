package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.dto.StaticToolDto;

import java.util.List;

public interface StaticToolDao {
    StaticToolDto createNew(StaticToolDto staticToolDto);
    StaticToolDto update(StaticToolDto staticToolDto);
    StaticToolDto findById(Long id);
    StaticToolDto findByName(String name);
    List<StaticToolDto> getAll();
    StaticToolDto delete(Long id);
    boolean exists(Long id);
    boolean existsByName(String name);
    List<StaticToolDto> findByVersion(String version);
}
