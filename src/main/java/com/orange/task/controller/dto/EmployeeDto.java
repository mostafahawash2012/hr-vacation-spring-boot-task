package com.orange.task.controller.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "Employee", description = "Employee DTO")
public class EmployeeDto {

	@JsonView(Views.Basic.class)
	private UUID Id;

	@JsonView(Views.Basic.class)
	private String name;

	@JsonView(Views.Detail.class)
	private int totalBalance = 30;

	@JsonView(Views.Detail.class)
	private int remainingBalance;

	@JsonView(Views.Detail.class)
	private Set<HistoryDto> vacationHistory = new HashSet<>();
}
