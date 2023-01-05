package com.central.stores.employees.test.utils;

import java.time.LocalDate;
import java.util.UUID;

import com.central.stores.employees.model.Address;
import com.central.stores.employees.model.Employee;
import com.central.stores.employees.model.dto.AddressDTO;
import com.central.stores.employees.model.dto.RequestEmployeeDTO;
import com.central.stores.employees.model.dto.ResponseEmployeeDTO;

public final class ClassBuilder {

	public static Address addressBuilder() {
		Address address = new Address(); 
		
		address.setNumber(333);
		address.setCity("Teste city");
		address.setStreet("Teste rua");
		address.setId(UUID.randomUUID());
		address.setChanged(LocalDate.now());
		address.setCreated(LocalDate.now());
		address.setNeighborhood("Teste bairro");
		
		return address;
	}
	
	public static AddressDTO addressDTOBuilder() {
		AddressDTO addressDTO = new AddressDTO();
		
		addressDTO.setNumber(333);
		addressDTO.setCity("Teste city");
		addressDTO.setStreet("Teste rua");
		addressDTO.setNeighborhood("Teste bairro");
		
		return addressDTO;
	}
	
	public static Employee employeeBuilder() {
		Employee employee = new Employee();
		
		employee.setId(UUID.randomUUID());
		employee.setGender("M");
		employee.setActive(true);
		employee.setName("Teste");
		employee.setCpf("123456789");
		employee.setRg("5544669878");
		employee.setRole("testador");
		employee.setPhone("987654321");
		employee.setChanged(LocalDate.now());
		employee.setCreated(LocalDate.now());
		employee.setEmail("teste@teste.com");
		
		return employee;
	}
	
	public static RequestEmployeeDTO requestEmployeeDTOBuilder() {
		RequestEmployeeDTO requestEmployeeDTO = new RequestEmployeeDTO();

		requestEmployeeDTO.setGender("M");
		requestEmployeeDTO.setName("Teste");
		requestEmployeeDTO.setCpf("123456789");
		requestEmployeeDTO.setRg("5544669878");
		requestEmployeeDTO.setRole("testador");
		requestEmployeeDTO.setPhone("987654321");
		requestEmployeeDTO.setEmail("teste@teste.com");
		
		return requestEmployeeDTO;
	}
	
	public static ResponseEmployeeDTO responseEmployeeDTOBuilder() {
		ResponseEmployeeDTO responseEmployeeDTO = new ResponseEmployeeDTO();

		responseEmployeeDTO.setName("Teste");
		responseEmployeeDTO.setId(UUID.randomUUID());
		
		return responseEmployeeDTO;
	}
}
