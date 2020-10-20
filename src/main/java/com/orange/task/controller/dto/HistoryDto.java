package com.orange.task.controller.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "History", description = "History DTO")
public class HistoryDto {

	@JsonView(Views.Detail.class)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date startDate;

	@JsonView(Views.Detail.class)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
}
