package com.central.stores.employees.services.implement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.central.stores.employees.crypto.Cryptography;
import com.central.stores.employees.exception.DuplicateDocumentsException;
import com.central.stores.employees.exception.ResourceNotFoundException;
import com.central.stores.employees.mapper.EmployeeMapper;
import com.central.stores.employees.model.Employee;
import com.central.stores.employees.model.dto.RequestEmployeeDTO;
import com.central.stores.employees.model.dto.ResponseEmployeeDTO;
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

public class EmployeesServicesImplementTest {

	@InjectMocks
	private EmployeesServicesImplement services;
	
	@Mock
	private EmployeeMapper mapper;

	@Mock
	private Validator mockValidator;
	
	@Mock
	private EmployeesRepository repository;

	private Employee employee;

	private LocalValidatorFactoryBean validator;

	private RequestEmployeeDTO requestEmployeeDTO;
	
	private ResponseEmployeeDTO responseEmployeeDTO;
	
	private Set<ConstraintViolation<Object>> violations;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		employee = ClassBuilder.employeeBuilder();
		requestEmployeeDTO = ClassBuilder.requestEmployeeDTOBuilder();
		responseEmployeeDTO = ClassBuilder.responseEmployeeDTOBuilder();
		
		validator = new LocalValidatorFactoryBean();
		validator.afterPropertiesSet();
	}
	
	@Test
	public void findByCpf() {
		employee = Cryptography.encode(employee);
		when(repository.findByCpf(anyString())).thenReturn(employee);
		Employee emp = services.findByCpf("123456789");

		assertEquals(emp, employee);
	}
	
	@Test
	public void findByCpfResourceNotFoundException() {
		String messageError = "No record found for this cpf";
		employee = Cryptography.encode(employee);
		
		when(repository.findByCpf(anyString())).thenReturn(null);
		
		String message = assertThrows(ResourceNotFoundException.class, () -> {
			services.findByCpf("123456789");
		}).getMessage();

		assertEquals(messageError, message);
	}
	
	@Test
	public void findByNeighborhood() {
		employee = Cryptography.encode(employee);
		when(repository.findAllByActiveTrueAndAddressNeighborhood(anyString()))
		.thenReturn(List.of(employee));
		List<Employee> empList = services.findByNeighborhood("teste");
		
		assertNotNull(empList);
	}
	
	@Test
	public void findByNeighborhoodResourceNotFoundException() {
		String messageError = "No record found for this neighborhood";
		employee = Cryptography.encode(employee);
		when(repository.findAllByActiveTrueAndAddressNeighborhood(anyString()))
		.thenReturn(new ArrayList<Employee>());
		
		String message = assertThrows(ResourceNotFoundException.class, () -> {
			services.findByNeighborhood("teste");
		}).getMessage();
		
		assertEquals(messageError, message);
	}
	
	@Test
	public void findAll() {
		employee = Cryptography.encode(employee);
		when(repository.findAllByActiveTrue()).thenReturn(List.of(employee));
		List<Employee> empList = services.findAll();
		assertNotNull(empList);
	}
	
	@Test
	public void delete() {
		employee.setActive(false);
		employee = Cryptography.encode(employee);
		when(mapper.employeeDelete(any())).thenReturn(employee);
		when(repository.findById(any())).thenReturn(Optional.of(employee));
		Employee emp = (Employee) services.delete(UUID.randomUUID());
		
		assertTrue(emp.getActive().equals(false));
	}
	@Test
	public void deleteResourceNotFoundException() {
		String messageError = "No records found for this id";
		
		employee = Cryptography.encode(employee);
		when(repository.findById(any())).thenReturn(Optional.ofNullable(null));
		
		String message = assertThrows(ResourceNotFoundException.class, () -> {
			services.delete(UUID.randomUUID());
		}).getMessage();
		
		assertEquals(messageError, message);
	}
	
	@Test
	public void create() {
		when(mapper.toModel(any())).thenReturn(employee);
		when(mapper.modelToResponseEmployeeDTO(any())).thenReturn(responseEmployeeDTO);
		ResponseEntity<Object> emp = services.create(requestEmployeeDTO);
		
		assertTrue(emp.getStatusCode().equals(HttpStatus.CREATED));
	}
	
	@Test
	public void createWithMissingFields() {
		requestEmployeeDTO.setCpf(null);
		
		violations = validator.validate(requestEmployeeDTO);
		when(mockValidator.validate(any())).thenReturn(violations);
		ResponseEntity<Object> emp = services.create(requestEmployeeDTO);
		
		assertTrue(emp.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY));
	}
	
	@Test
	public void createDuplicateDocumentsException() {
		String messageError = "Documents already registered";
		
		when(mapper.toModel(any())).thenReturn(employee);
		when(mapper.modelToResponseEmployeeDTO(any())).thenReturn(responseEmployeeDTO);
		when(repository.findByRg(anyString())).thenReturn(employee);
		when(repository.findByCpf(anyString())).thenReturn(employee);
		
		String message = assertThrows(DuplicateDocumentsException.class, () -> {
			services.create(requestEmployeeDTO);
		}).getMessage();
		
		assertEquals(messageError, message);
	}
	
	@Test
	public void createWithDuplicateCpf() {
		String messageError = "Cpf already registered";
		
		when(mapper.toModel(any())).thenReturn(employee);
		when(mapper.modelToResponseEmployeeDTO(any())).thenReturn(responseEmployeeDTO);
		when(repository.findByCpf(anyString())).thenReturn(employee);
		
		String message = assertThrows(DuplicateDocumentsException.class, () -> {
			services.create(requestEmployeeDTO);
		}).getMessage();
		
		assertEquals(messageError, message);
	}
	@Test
	public void createWithDuplicateRg() {
		String messageError = "Rg already registered";
		
		when(mapper.toModel(any())).thenReturn(employee);
		when(mapper.modelToResponseEmployeeDTO(any())).thenReturn(responseEmployeeDTO);
		when(repository.findByRg(anyString())).thenReturn(employee);
		
		String message = assertThrows(DuplicateDocumentsException.class, () -> {
			services.create(requestEmployeeDTO);
		}).getMessage();
		
		assertEquals(messageError, message);
	}
	
	@Test
	public void update() {
		when(repository.findById(any())).thenReturn(Optional.of(employee));
		when(mapper.modelToResponseEmployeeDTO(any())).thenReturn(responseEmployeeDTO);
		ResponseEntity<Object> emp = services.update(requestEmployeeDTO, UUID.randomUUID());
		
		assertTrue(emp.getStatusCode().equals(HttpStatus.NO_CONTENT));
	}

	@Test
	public void updateWithMissingFields() {
		requestEmployeeDTO.setCpf(null);
		
		violations = validator.validate(requestEmployeeDTO);
		when(mockValidator.validate(any())).thenReturn(violations);
		ResponseEntity<Object> emp = services.update(requestEmployeeDTO, UUID.randomUUID());
		
		assertTrue(emp.getStatusCode().equals(HttpStatus.UNPROCESSABLE_ENTITY));
	}
	
	@Test
	public void updateResourceNotFoundException() {
		String messageError = "No records found for this id";
		
		when(repository.findById(any())).thenReturn(Optional.ofNullable(null));
		
		String message = assertThrows(ResourceNotFoundException.class, () -> {
			services.update(requestEmployeeDTO, UUID.randomUUID());
		}).getMessage();
		
		assertEquals(messageError, message);
	}
}
