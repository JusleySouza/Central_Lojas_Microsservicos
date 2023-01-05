package com.central.stores.employees.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.central.stores.employees.test.utils.ClassBuilder;

public class ResponseEmployeeDTOTest {

	private UUID id;
	
	private ResponseEmployeeDTO expectedResponseEmployeeDTO;
	
	@BeforeEach
	void setUp() {
		id = UUID.randomUUID();
		expectedResponseEmployeeDTO = ClassBuilder.responseEmployeeDTOBuilder();
		expectedResponseEmployeeDTO.setId(id);
	}
	
	@Test
	void setter() {
		ResponseEmployeeDTO responseEmployeeDTO = new ResponseEmployeeDTO();
		responseEmployeeDTO.setId(id);
		responseEmployeeDTO.setName("Teste");
		
		assertEquals(expectedResponseEmployeeDTO.toString(), responseEmployeeDTO.toString());
	}
}
