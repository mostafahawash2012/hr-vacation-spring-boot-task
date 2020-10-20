package com.orange.task.controller.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.orange.task.controller.dto.EmployeeDto;
import com.orange.task.controller.dto.HistoryDto;
import com.orange.task.model.Employee;
import com.orange.task.model.History;

@Component
public class EmployeeMapper implements EntityMapper<Employee, EmployeeDto> {

	@Autowired
	EntityMapper<History, HistoryDto> historyMapper;

	@Override
	public EmployeeDto toDto(Employee entity) {
		EmployeeDto dto = new EmployeeDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setRemainingBalance(entity.getRemainingBalance());
		dto.setTotalBalance(entity.getTotalBalance());
		entity.getVacationHistory().forEach(history -> dto.getVacationHistory().add(historyMapper.toDto(history)));
		return dto;
	}

	@Override
	public Employee toEntity(EmployeeDto dto) {

		Employee entity = new Employee();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setRemainingBalance(dto.getRemainingBalance());
		entity.setTotalBalance(dto.getTotalBalance());
		dto.getVacationHistory()
				.forEach(historyDto -> entity.getVacationHistory().add(historyMapper.toEntity(historyDto)));

		return entity;
	}

}
