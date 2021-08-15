package com.skeleton.account.mapper.abstraction;

import org.modelmapper.ModelMapper;

import java.lang.reflect.ParameterizedType;

public abstract class AbstractEntityDtoConverter<E, D> implements EntityDtoConverter<E, D> {

    private final ModelMapper modelMapper;
    private final Class<E> entityClass;
    private final Class<D> dtoClass;

    public AbstractEntityDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.entityClass = getEntityClass();
        this.dtoClass = getDtoClass();
        this.modelMapper.typeMap(this.entityClass, this.dtoClass);
    }

    @SuppressWarnings("unchecked")
    private Class<E> getEntityClass() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    private Class<D> getDtoClass() {
        return (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    public E toEntity(D dto) {
        return modelMapper.map(dto, entityClass);
    }

    public D toDto(E entity) {
        return modelMapper.map(entity, dtoClass);
    }
}
