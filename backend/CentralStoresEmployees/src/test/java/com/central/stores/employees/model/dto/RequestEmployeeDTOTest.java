package com.central.stores.employees.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.central.stores.employees.test.utils.ClassBuilder;

public class RequestEmployeeDTOTest {

	private RequestEmployeeDTO expectedRequestEmployeeDTO;
	
	@BeforeEach
	void setUp() {
		expectedRequestEmployeeDTO = ClassBuilder.requestEmployeeDTOBuilder();
	}
	
	@Test
	void setter() {
		RequestEmployeeDTO requestEmployeeDTO = new RequestEmployeeDTO();
		requestEmployeeDTO.setGender("M");
		requestEmployeeDTO.setName("Teste");
		requestEmployeeDTO.setCpf("123456789");
		requestEmployeeDTO.setRg("5544669878");
		requestEmployeeDTO.setRole("testador");
		requestEmployeeDTO.setPhone("987654321");
		requestEmployeeDTO.setEmail("teste@teste.com");
		
		assertEquals(expectedRequestEmployeeDTO.toString(), requestEmployeeDTO.toString());
	}
}
