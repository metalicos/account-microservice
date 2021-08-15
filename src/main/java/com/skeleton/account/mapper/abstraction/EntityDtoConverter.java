package com.skeleton.account.mapper.abstraction;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public interface EntityDtoConverter<E, D> {

    E toEntity(D dto);

    D toDto(E entity);

    default List<E> toEntityList(List<D> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) {
            return Collections.emptyList();
        }
        return dtoList.stream().map(this::toEntity).collect(toList());
    }

    default List<D> toDtoList(List<E> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return Collections.emptyList();
        }
        return entityList.stream().map(this::toDto).collect(toList());
    }

    default Set<E> toEntitySet(Set<D> dtoSet) {
        if (dtoSet == null || dtoSet.isEmpty()) {
            return Collections.emptySet();
        }
        return dtoSet.stream().map(this::toEntity).collect(toSet());
    }

    default Set<D> toDtoSet(Set<E> entitySet) {
        if (entitySet == null || entitySet.isEmpty()) {
            return Collections.emptySet();
        }
        return entitySet.stream().map(this::toDto).collect(toSet());
    }
}
