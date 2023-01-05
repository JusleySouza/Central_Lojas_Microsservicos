package com.central.stores.employees.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.central.stores.employees.test.utils.ClassBuilder;

public class EmployeeTest {

	private UUID id;
	
	private Address address;
	
	private Employee expectedEmployee;
	
	@BeforeEach
	void setUp() {
		address = ClassBuilder.addressBuilder();
		expectedEmployee = ClassBuilder.employeeBuilder();
		id = UUID.randomUUID();
		expectedEmployee.setId(id);
		expectedEmployee.setAddress(address);
	}
	
	@Test
	void setter() {
		Employee employee = new Employee();
		employee.setId(id);
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
		employee.setAddress(address);
		
		assertEquals(expectedEmployee.toString(), employee.toString());
	}
}
