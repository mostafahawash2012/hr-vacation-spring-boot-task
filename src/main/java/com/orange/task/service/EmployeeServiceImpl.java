package com.orange.task.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orange.task.exception.ForbiddenOperationException;
import com.orange.task.exception.RecordNotFoundException;
import com.orange.task.model.Employee;
import com.orange.task.model.History;
import com.orange.task.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	@Transactional
	public Employee create(Employee employee) {
		int totalNumberOfVacations = getTotalNumberOfDays(employee);

		if (totalNumberOfVacations <= employee.getRemainingBalance()) {
			employee.setRemainingBalance(employee.getRemainingBalance() - totalNumberOfVacations);
		} else {
			throw new ForbiddenOperationException("Employee doesn't have enough balance");
		}

		employee.setRemainingBalance(employee.getRemainingBalance() - totalNumberOfVacations);
		return employeeRepository.save(employee);
	}

	@Override
	public Employee update(UUID id, Employee employee) {
		Optional<Employee> updateEmployee = employeeRepository.findById(id);
		if (updateEmployee.isPresent()) {
			employee.setId(id);
			int totalNumberOfVacations = getTotalNumberOfDays(employee);
			if (totalNumberOfVacations <= employee.getRemainingBalance()) {
				employee.setRemainingBalance(employee.getRemainingBalance() - totalNumberOfVacations);
			} else {
				throw new ForbiddenOperationException("Employee doesn't have enough balance id= " + id);
			}
			log.info("Updating employee with id={}", id);
			return employeeRepository.save(employee);
		} else {
			throw new RecordNotFoundException("Employee not found Id = " + id);
		}
	}

	@Override
	public Employee addVacation(UUID id, History history) {
		Optional<Employee> findEmployee = employeeRepository.findById(id);
		if (findEmployee.isPresent()) {
			Employee updatedEmployee = findEmployee.get();

			int numberOfDays = getVacationNumberOfDays(history);

			if (numberOfDays <= updatedEmployee.getRemainingBalance()) {
				log.info("Adding vacation to employee with id={}", id);
				updatedEmployee.setRemainingBalance(updatedEmployee.getRemainingBalance() - numberOfDays);
				updatedEmployee.getVacationHistory().add(history);
			} else {
				throw new ForbiddenOperationException("Employee doesn't have enough balance id=" + id);
			}
			return employeeRepository.save(updatedEmployee);
		} else {
			throw new RecordNotFoundException("Employee not found Id = " + id);
		}
	}

	@Override
	public void delete(UUID id) {
		Optional<Employee> deleteEmployee = employeeRepository.findById(id);
		if (deleteEmployee.isPresent()) {
			employeeRepository.deleteById(id);
			log.info("Employee with id={} has been deleted.", id);
		} else {
			throw new RecordNotFoundException("Employee not found Id = " + id);
		}

	}

	@Override
	public Employee getEmployee(UUID id) {
		Optional<Employee> findEmployee = employeeRepository.findById(id);
		if (findEmployee.isPresent()) {
			log.info("Retrieving employee with id={}.", id);
			return findEmployee.get();
		} else {
			throw new RecordNotFoundException("Employee not found Id = " + id);
		}
	}

	@Override
	public List<Employee> getAllEmployees(String name) {
		if (name != null) {
			log.info("Retrieving all employees with name = {}", name);
			return employeeRepository.findAllByNameContainingIgnoreCase(name);
		} else {
			log.info("Retrieving all employees.");
			return employeeRepository.findAll();
		}
	}

	private int getTotalNumberOfDays(Employee employee) {
		int totalNumOfDays = 0;
		if (employee.getVacationHistory().size() > 0) {

			for (History history : employee.getVacationHistory()) {
				totalNumOfDays += getVacationNumberOfDays(history);
			}
			if (totalNumOfDays > employee.getRemainingBalance()) {
				log.error("Employee doesn't have enough balance");
				throw new ForbiddenOperationException("Employee doesn't have enough balance");
			}
		}
		return totalNumOfDays;
	}

	private int getVacationNumberOfDays(History history) {
		int numberOfDays = 0;

		if (!history.getStartDate().before(history.getEndDate())) {
			throw new ForbiddenOperationException("Start Date has to be before End date.");
		}

		Date startDate = history.getStartDate();
		Date endDate = history.getEndDate();
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		startCal.setTime(startDate);
		endCal.setTime(endDate);

		while (startCal.before(endCal)) {
			if ((Calendar.SATURDAY != startCal.get(Calendar.DAY_OF_WEEK))
					&& (Calendar.FRIDAY != startCal.get(Calendar.DAY_OF_WEEK))) {
				numberOfDays++;
			}
			startCal.add(Calendar.DATE, 1); // Increment calendar
		}

		return numberOfDays;
	}

}
