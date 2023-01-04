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

import com.central.stores.employees.model.Address;
import com.central.stores.employees.services.implement.AddressServicesImplent;
import com.central.stores.employees.test.utils.ClassBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(controllers = AddressController.class)
public class AddressControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AddressServicesImplent services;

	private Address address;

	private ObjectMapper objectMapper;
	
	private final String CONTEXT_PATH = "/employees";
	
	private final String EMPLOYEE_ID = "/aef8cdce-7ca2-4c76-8620-c53efeba21fc";
	
	private final String PATH = "/address";
	
	private final String ADDRESS_ID = "/4074f2a7-2aa1-42f3-b26c-5edaff83590c";

	@BeforeEach
	void setUp() {
		address = ClassBuilder.addressBuilder();
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
	}

	@Test
	void create() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post(CONTEXT_PATH + EMPLOYEE_ID + PATH)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(address)))
				.andExpect(status().isOk());
	}
	
	@Test
	void update() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put(CONTEXT_PATH + EMPLOYEE_ID + PATH + ADDRESS_ID)
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(address)))
				.andExpect(status().isOk());
	}
}
