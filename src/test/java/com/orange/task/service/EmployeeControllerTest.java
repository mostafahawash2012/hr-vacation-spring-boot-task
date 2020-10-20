package com.orange.task.service;

import java.text.SimpleDateFormat;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orange.task.controller.dto.EmployeeDto;
import com.orange.task.controller.dto.HistoryDto;
import com.orange.task.model.Employee;
import com.orange.task.repository.EmployeeRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class EmployeeControllerTest {

	private static final String URL = "/employees";
	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	EmployeeRepository employeeRepo;

	@Autowired
	MockMvc mvc;

	private Employee updatedEmployee;

	@BeforeEach
	public void setup() {
		updatedEmployee = new Employee();
		Employee newEmployee = new Employee();
		updatedEmployee = employeeRepo.save(updatedEmployee);
		newEmployee = employeeRepo.save(newEmployee);
	}

	@Test
	public void testCreate() throws Exception {
		EmployeeDto createdEmployee = new EmployeeDto();
		createdEmployee.setName("Employee_1");
		final ResultActions result = mvc.perform(MockMvcRequestBuilders.post(URL)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(createdEmployee)));
		result.andExpect(MockMvcResultMatchers.status().isCreated()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testCreateWithInvalidData() throws Exception {
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.setName("Employee 2");
		final ResultActions result = mvc.perform(MockMvcRequestBuilders.post(URL)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(null)));
		result.andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testDelete() throws Exception {

		Employee newEmployee = new Employee();
		newEmployee = employeeRepo.save(newEmployee);
		final ResultActions result = mvc.perform(MockMvcRequestBuilders.delete(URL + "/" + newEmployee.getId()));
		result.andExpect(MockMvcResultMatchers.status().isNoContent()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testGetAllResources() throws Exception {

		final ResultActions result = mvc.perform(MockMvcRequestBuilders.get(URL));

		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()", Matchers.greaterThan(1)))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testUpdate() throws Exception {
		final ResultActions result = mvc.perform(MockMvcRequestBuilders.put(URL + "/" + updatedEmployee.getId())
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedEmployee)));

		result.andExpect(MockMvcResultMatchers.status().isNoContent()).andDo(MockMvcResultHandlers.print());

	}

	@Test
	public void testAddVacation() throws Exception {
		String s = "2020-10-25";
		String e = "2020-10-26";
		HistoryDto historyDto = new HistoryDto();
		historyDto.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse(s));
		historyDto.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse(e));
		final ResultActions result = mvc
				.perform(MockMvcRequestBuilders.put(URL + "/" + updatedEmployee.getId() + "/vacation")
						.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(historyDto)));

		result.andExpect(MockMvcResultMatchers.status().isNoContent()).andDo(MockMvcResultHandlers.print());

	}
}
