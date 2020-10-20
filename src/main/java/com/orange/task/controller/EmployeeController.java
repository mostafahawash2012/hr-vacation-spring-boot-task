package com.orange.task.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.orange.task.controller.dto.EmployeeDto;
import com.orange.task.controller.dto.HistoryDto;
import com.orange.task.controller.dto.Views;
import com.orange.task.controller.mapper.EntityMapper;
import com.orange.task.exception.MissingDataException;
import com.orange.task.model.Employee;
import com.orange.task.model.History;
import com.orange.task.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(path = "/employees")
@Api(tags = { "Employee" })
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EntityMapper<Employee, EmployeeDto> employeeMapper;

	@Autowired
	private EntityMapper<History, HistoryDto> historyMapper;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@JsonView(Views.Detail.class)
	@ApiOperation(value = "Create an Employee")
	EmployeeDto create(@RequestBody final EmployeeDto employeeDto) {

		if (employeeDto != null) {
			Employee createdEmployee = employeeService.create(employeeMapper.toEntity(employeeDto));
			return employeeMapper.toDto(createdEmployee);
		} else {
			throw new MissingDataException("You have to provied a valid JSON resource.");
		}
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Delete an employee")
	public void deleteResource(
			@ApiParam(value = "Id of employee", type = "string", allowMultiple = false) @PathVariable final UUID id) {
		employeeService.delete(id);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(Views.Basic.class)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get all employees")
	public List<EmployeeDto> getAllEmployee(@RequestParam(required = false) final String name) {

		return employeeMapper.toDto(employeeService.getAllEmployees((name)));
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@JsonView(Views.Detail.class)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "get employee")
	public EmployeeDto getEmployee(
			@ApiParam(value = "Id of employee", type = "string", allowMultiple = false) @PathVariable final UUID id) {
		return employeeMapper.toDto(employeeService.getEmployee(id));
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "update employee")
	public EmployeeDto update(
			@ApiParam(value = "Id of employee", type = "string", allowMultiple = false) @PathVariable final UUID id,
			@RequestBody final EmployeeDto employeeDto) {

		return employeeMapper.toDto(employeeService.update(id, employeeMapper.toEntity(employeeDto)));
	}

	@PutMapping(path = "/{id}/vacation", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Add new vacation to employee")
	public EmployeeDto addVacation(
			@ApiParam(value = "Id of employee", type = "string", allowMultiple = false) @PathVariable final UUID id,
			@RequestBody final HistoryDto historyDto) {

		return employeeMapper.toDto(employeeService.addVacation(id, historyMapper.toEntity(historyDto)));
	}

}
