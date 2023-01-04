package com.central.stores.employees.services.implement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.central.stores.employees.exception.ResourceNotFoundException;
import com.central.stores.employees.mapper.AddressMapper;
import com.central.stores.employees.model.Address;
import com.central.stores.employees.model.Employee;
import com.central.stores.employees.model.dto.AddressDTO;
import com.central.stores.employees.repository.AddressRepository;
import com.central.stores.employees.repository.EmployeesRepository;
import com.central.stores.employees.test.utils.ClassBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

public class AddressServicesImplentTest {
	@InjectMocks
	private AddressServicesImplent services;

	@Mock
	private Validator mockValidator;

	@Mock
	private AddressMapper mapper;

	@Mock
	private AddressRepository addressRepository;

	@Mock
	private EmployeesRepository employeesRepository;
	
	private Address address;

	private Employee employee;

	private AddressDTO requestAddressDTO;
	
	private LocalValidatorFactoryBean validator;
	
	private Set<ConstraintViolation<Object>> violations;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		address = ClassBuilder.addressBuilder();
		employee = ClassBuilder.employeeBuilder();
		requestAddressDTO = ClassBuilder.addressDTOBuilder();
		
		validator = new LocalValidatorFactoryBean();
		validator.afterPropertiesSet();
	}

	
	@Test
	void create() {
		when(mapper.toModel(any())).thenReturn(address);
		when(employeesRepository.findById(any())).thenReturn(Optional.of(employee));
		ResponseEntity<Object> address = services.create(requestAddressDTO, UUID.randomUUID());
		
		assertTrue(address.getStatusCode().equals(HttpStatus.CREATED));
	}
	
	@Test
	void createWithMissingFields() {
		requestAddressDTO.setNumber(null);
		
		violations = validator.validate(requestAddressDTO);
		when(mockValidator.validate(any())).thenReturn(violations);
		ResponseEntity<Object> address = services.create(requestAddressDTO, UUID.randomUUID());
		
		assertTrue(address.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY));
	}
	
	@Test
	void update() {
		when(addressRepository.findById(any())).thenReturn(Optional.of(address));
		ResponseEntity<Object> address = services.update(requestAddressDTO, UUID.randomUUID());
		
		assertTrue(address.getStatusCode().equals(HttpStatus.NO_CONTENT));
	}
	
	@Test
	void updateWithMissingFields() {
		requestAddressDTO.setNumber(null);
		
		violations = validator.validate(requestAddressDTO);
		when(mockValidator.validate(any())).thenReturn(violations);
		ResponseEntity<Object> address = services.update(requestAddressDTO, UUID.randomUUID());
		
		assertTrue(address.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY));
	}
	
	@Test
	void updateResourceNotFoundException() {
		String messageError = "No records found for this id";
		
		when(addressRepository.findById(any())).thenReturn(Optional.ofNullable(null));
		
		String message = assertThrows(ResourceNotFoundException.class, () -> {
			services.update(requestAddressDTO, UUID.randomUUID());
		}).getMessage();
		
		assertEquals(messageError, message);
	}
}
