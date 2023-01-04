package com.central.stores.employees.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.central.stores.employees.model.Employee;
import com.central.stores.employees.services.implement.EmployeesServicesImplement;
import com.central.stores.employees.test.utils.ClassBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(controllers = EmployeesController.class)
public class EmployeesControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EmployeesServicesImplement services;
	
	private ObjectMapper objectMapper;
	
	private Employee employee;
	
	private final String CONTEXT_PATH = "/employees";
	
	private final String PATH_FIND_ALL = "/list";
	
	private final String EMPLOYEE_CPF = "12345678910";
	
	private final String QUERY_PARAM_KEY = "neighborhood";
	
	private final String QUERY_PARAM_VALUE = "teste";

	private final String EMPLOYEE_ID = "/aef8cdce-7ca2-4c76-8620-c53efeba21fc";
	
	
	@BeforeEach
	void setUp() {
		objectMapper = new ObjectMapper();
		employee = ClassBuilder.employeeBuilder();
		objectMapper.registerModule(new JavaTimeModule());
	}
	
	@Test
	void listEmployees() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(CONTEXT_PATH + PATH_FIND_ALL))
		.andExpect(status().isOk());
	}
	
	@Test
	void findByCPF() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(CONTEXT_PATH + PATH_FIND_ALL + EMPLOYEE_CPF))
		.andExpect(status().isOk());
	}
	
	@Test
	void findByNeighborhood() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get(CONTEXT_PATH).param(QUERY_PARAM_KEY, QUERY_PARAM_VALUE))
		.andExpect(status().isOk());
	}
	
	@Test
	void create() throws Exception {
	mockMvc.perform(MockMvcRequestBuilders.post(CONTEXT_PATH)
			.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee)))
			.andExpect(status().isOk());
	}
	
	@Test
	void update()throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put(CONTEXT_PATH + EMPLOYEE_ID)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employee)))
				.andExpect(status().isOk());
	}
	
	@Test
	void delete()throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete(CONTEXT_PATH + EMPLOYEE_ID))
				.andExpect(status().isNoContent());
	}
}
