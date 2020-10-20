package com.orange.task.controller.mapper;

import org.springframework.stereotype.Component;

import com.orange.task.controller.dto.HistoryDto;
import com.orange.task.model.History;

@Component
public class HistoryMapper implements EntityMapper<History, HistoryDto> {

	@Override
	public HistoryDto toDto(History entity) {

		HistoryDto dto = new HistoryDto();
		dto.setEndDate(entity.getEndDate());
		dto.setStartDate(entity.getStartDate());

		return dto;
	}

	@Override
	public History toEntity(HistoryDto dto) {

		History entity = new History();
		entity.setEndDate(dto.getEndDate());
		entity.setStartDate(dto.getStartDate());

		return entity;
	}

}
