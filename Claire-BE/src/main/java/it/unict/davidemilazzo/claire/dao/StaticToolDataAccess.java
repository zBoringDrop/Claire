package it.unict.davidemilazzo.claire.dao;

import it.unict.davidemilazzo.claire.dto.StaticToolDto;
import it.unict.davidemilazzo.claire.exception.ExceptionMessages;
import it.unict.davidemilazzo.claire.exception.IdNotFoundException;
import it.unict.davidemilazzo.claire.mapper.ToolMapper;
import it.unict.davidemilazzo.claire.model.StaticToolEntity;
import it.unict.davidemilazzo.claire.model.ToolType;
import it.unict.davidemilazzo.claire.respository.StaticToolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository("statictool-mysql")
public class StaticToolDataAccess implements StaticToolDao {

    private final StaticToolRepository staticToolRepository;

    @Override
    public StaticToolDto createNew(StaticToolDto staticToolDto) {
        StaticToolEntity staticToolEntity = ToolMapper.INSTANCE.toEntity(staticToolDto);
        staticToolEntity.setType(ToolType.STATIC);
        return ToolMapper.INSTANCE.toDto(staticToolRepository.save(staticToolEntity));
    }

    @Override
    public StaticToolDto update(StaticToolDto staticToolDto) {
        return null;
    }

    @Override
    public StaticToolDto findById(Long id) {
        StaticToolEntity staticToolEntity = staticToolRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        return ToolMapper.INSTANCE.toDto(staticToolEntity);
    }

    @Override
    public StaticToolDto findByName(String name) {
        Optional<StaticToolEntity> oStaticToolEntity = staticToolRepository.findByName(name);
        if (oStaticToolEntity.isEmpty()) {
            throw new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND);
        }
        return ToolMapper.INSTANCE.toDto(oStaticToolEntity.get());
    }

    @Override
    public List<StaticToolDto> getAll() {
        return ToolMapper.INSTANCE.toDtoListStatic(staticToolRepository.findAll());
    }

    @Override
    public StaticToolDto delete(Long id) {
        StaticToolEntity staticToolEntityToDelete = staticToolRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(ExceptionMessages.ID_NOT_FOUND));
        StaticToolDto staticToolDto = ToolMapper.INSTANCE.toDto(staticToolEntityToDelete);
        staticToolRepository.delete(staticToolEntityToDelete);
        return staticToolDto;
    }

    @Override
    public boolean exists(Long id) {
        return staticToolRepository.existsById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return staticToolRepository.existsByName(name);
    }

    @Override
    public List<StaticToolDto> findByVersion(String version) {
        List<StaticToolEntity> staticToolEntities = staticToolRepository.findByVersion(version);
        return ToolMapper.INSTANCE.toDtoListStatic(staticToolEntities);
    }
}
