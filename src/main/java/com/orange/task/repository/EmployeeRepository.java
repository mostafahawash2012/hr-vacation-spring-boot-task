package com.orange.task.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orange.task.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

	List<Employee> findAllByNameContainingIgnoreCase(final String name);
}
