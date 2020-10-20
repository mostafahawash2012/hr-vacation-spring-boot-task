package com.orange.task.service;

import java.util.List;
import java.util.UUID;

import com.orange.task.model.Employee;
import com.orange.task.model.History;

public interface EmployeeService {

	Employee create(final Employee employee);

	Employee update(final UUID id, final Employee employee);

	Employee addVacation(UUID id, History history);

	void delete(final UUID id);

	Employee getEmployee(final UUID id);

	List<Employee> getAllEmployees(final String name);
}
