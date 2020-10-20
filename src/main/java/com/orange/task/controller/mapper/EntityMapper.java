package com.orange.task.controller.mapper;

import java.util.ArrayList;
import java.util.List;

/**
 * EntityMapper interface that a mapper implementation between DTOs and entities
 * will inherit
 *
 * @param <E> The entity object
 * @param <D> The DTO object
 */
public interface EntityMapper<E, D> {

	D toDto(final E entity);

	E toEntity(final D dto);

	default List<D> toDto(final List<E> entities) {
		List<D> dtos = new ArrayList<>();
		entities.forEach(entity -> dtos.add(toDto(entity)));
		return dtos;
	}

	default List<E> toEntity(final List<D> dtos) {
		List<E> entities = new ArrayList<>();
		dtos.forEach(dto -> entities.add(toEntity(dto)));
		return entities;
	}
}
