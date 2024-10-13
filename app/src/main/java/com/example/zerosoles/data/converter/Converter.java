package com.example.zerosoles.data.converter;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Converter<TEntity, TDto> {

    private final Function<TDto, TEntity> fromDto;
    private final Function<TEntity, TDto> fromEntity;

    public Converter(final Function<TDto, TEntity> fromDto, final Function<TEntity, TDto> fromEntity) {
        this.fromDto = fromDto;
        this.fromEntity = fromEntity;
    }

    public final TEntity fromDto(final TDto dto) {
        return fromDto.apply(dto);
    }

    public final TDto fromEntity(final TEntity entity) {
        return fromEntity.apply(entity);
    }

    public final List<TEntity> fromDtos(final Collection<TDto> dtos) {
        return dtos.stream().map(this::fromDto).collect(Collectors.toList());
    }

    public final List<TDto> fromEntities(final Collection<TEntity> entities) {
        return entities.stream().map(this::fromEntity).collect(Collectors.toList());
    }
}
