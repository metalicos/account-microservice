package com.skeleton.account.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
public class EntityDtoConverter {

    private final ModelMapper modelMapper;
    private Class<?> entityClass;
    private Class<?> dtoClass;

    public EntityDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EntityDtoConverter setTypeMap(Class<?> entityClass, Class<?> dtoClass) {
        this.modelMapper.typeMap(entityClass, dtoClass);
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        return this;
    }

    public <Entity, Dto> Entity toEntity(Dto dto) {
        return modelMapper.map(dto, (Type) entityClass);
    }

    public <Entity, Dto> Dto toDto(Entity entity) {
        return modelMapper.map(entity, (Type) dtoClass);
    }

    public <Entity, Dto> List<Entity> toEntityList(List<Dto> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) {
            return Collections.emptyList();
        }
        return dtoList.stream()
                .map(this::<Entity, Dto>toEntity)
                .collect(toList());
    }

    public <Entity, Dto> List<Dto> toDtoList(List<Entity> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return Collections.emptyList();
        }
        return entityList.stream()
                .map(this::<Entity, Dto>toDto)
                .collect(toList());
    }

    public  <Entity, Dto> Set<Entity> toEntitySet(Set<Dto> dtoSet) {
        if (dtoSet == null || dtoSet.isEmpty()) {
            return Collections.emptySet();
        }
        return dtoSet.stream()
                .map(this::<Entity, Dto>toEntity)
                .collect(toSet());
    }

    public <Entity, Dto> Set<Dto> toDtoSet(Set<Entity> entitySet) {
        if (entitySet == null || entitySet.isEmpty()) {
            return Collections.emptySet();
        }
        return entitySet.stream()
                .map(this::<Entity, Dto>toDto)
                .collect(toSet());
    }
}
