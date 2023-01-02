package com.central.stores.employees.services.implement;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.central.stores.employees.mapper.AddressMapper;
import com.central.stores.employees.model.Address;
import com.central.stores.employees.model.Employee;
import com.central.stores.employees.model.dto.AddressDTO;
import com.central.stores.employees.repository.AddressRepository;
import com.central.stores.employees.repository.EmployeesRepository;
import com.central.stores.employees.test.utils.ClassBuilder;

public class AddressServicesImplentTest {
	@InjectMocks
	private AddressServicesImplent services;

	@Mock
	private AddressMapper mapper;

	@Mock
	private AddressRepository addressRepository;

	@Mock
	private EmployeesRepository employeesRepository;

	private Address address;

	private Employee employee;

	private AddressDTO requestAddressDTO;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		address = ClassBuilder.addressBuilder();
		employee = ClassBuilder.employeeBuilder();
		requestAddressDTO = ClassBuilder.addressDTOBuilder();
	}

	@Test
	void createTest() {
		when(mapper.toModel(any())).thenReturn(address);
		when(employeesRepository.findById(any())).thenReturn(Optional.of(employee));
		Address address = (Address) services.create(requestAddressDTO, UUID.randomUUID()).getBody();
		
		assertNotNull(address);
	}
	
	@Test
	void updateTest() {
		when(addressRepository.findById(any())).thenReturn(Optional.of(address));
		Address address = (Address) services.update(requestAddressDTO, UUID.randomUUID()).getBody();
		
		assertNotNull(address);
	}
}
