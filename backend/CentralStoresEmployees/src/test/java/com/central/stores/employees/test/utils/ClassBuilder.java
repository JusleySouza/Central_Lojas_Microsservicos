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
		return Address.builder()
				.id(UUID.randomUUID())
				.number(333)
				.city("Teste city")
				.street("Teste rua")
				.changed(LocalDate.now())
				.created(LocalDate.now())
				.neighborhood("Teste bairro")
				.build();
	}
	
	public static AddressDTO addressDTOBuilder() {
		return AddressDTO.builder()
				.number(333)
				.city("Teste city")
				.street("Teste rua")
				.neighborhood("Teste bairro")
				.build();
	}
	
	public static Employee employeeBuilder() {
		return Employee.builder()
				.id(UUID.randomUUID())
				.gender("M")
				.active(true)
				.name("Teste")
				.cpf("123456789")
				.rg("5544669878")
				.role("testador")
				.phone("987654321")
				.changed(LocalDate.now())
				.created(LocalDate.now())
				.email("teste@teste.com")
				.build();
	}
	
	public static RequestEmployeeDTO requestEmployeeDTOBuilder() {
		return RequestEmployeeDTO.builder()
				.gender("M")
				.name("Teste")
				.cpf("123456789")
				.rg("5544669878")
				.role("testador")
				.phone("987654321")
				.email("teste@teste.com")
				.build();
	}
	
	public static ResponseEmployeeDTO responseEmployeeDTOBuilder() {
		return ResponseEmployeeDTO.builder()
				.name("Teste")
				.id(UUID.randomUUID())
				.build();
	}
}
