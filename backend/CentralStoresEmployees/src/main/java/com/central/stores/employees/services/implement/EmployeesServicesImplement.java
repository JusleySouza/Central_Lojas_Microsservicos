package com.central.stores.employees.services.implement;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.central.stores.employees.config.LoggerConfig;
import com.central.stores.employees.crypto.Cryptography;
import com.central.stores.employees.exception.DuplicateDocumentsException;
import com.central.stores.employees.exception.ResourceNotFoundException;
import com.central.stores.employees.mapper.EmployeeMapper;
import com.central.stores.employees.mapper.UpdateModel;
import com.central.stores.employees.model.Employee;
import com.central.stores.employees.model.dto.RequestEmployeeDTO;
import com.central.stores.employees.model.dto.ResponseEmployeeDTO;
import com.central.stores.employees.model.dto.error.ResponseError;
import com.central.stores.employees.repository.EmployeesRepository;
import com.central.stores.employees.services.EmployeesServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class EmployeesServicesImplement implements EmployeesServices {
	@Autowired
	private EmployeeMapper mapper;

	@Autowired
	private EmployeesRepository repository;
	
	@Autowired
	private Validator validator;

	private Employee employee;

	private ResponseEmployeeDTO responseEmployeeDTO;
	
	@Override
	@Cacheable(cacheNames = "Employees", key="#root.method.name")
	public List<Employee> findAll() {
		List<Employee> listEmployees = repository.findAllByActiveTrue();
		
		listEmployees.forEach(employee -> employee = Cryptography.decode(employee));

		LoggerConfig.LOGGER_EMPLOYEE.info("Employee listing");

		return listEmployees;
	}

	@Override
	@Cacheable(cacheNames = "Employees", key="#employeeCpf")
	public Employee findByCpf(String employeeCpf) {
		employee = repository.findByCpf(Cryptography.encodeCpf(employeeCpf));
		
		if(employee == null) {
			LoggerConfig.LOGGER_EMPLOYEE.error("No record found for this cpf");
			throw new ResourceNotFoundException("No record found for this cpf");
		}

		employee = Cryptography.decode(employee);
		
		LoggerConfig.LOGGER_EMPLOYEE.info("Employee found");

		return employee;
	}

	@Override
	@Cacheable(cacheNames = "Employees", key="#neighborhood")
	public List<Employee> findByNeighborhood(String neighborhood) {
		List<Employee> listEmployees = repository.findAllByActiveTrueAndAddressNeighborhood(neighborhood);

		if(listEmployees.isEmpty()) {
			LoggerConfig.LOGGER_EMPLOYEE.error("No record found for this neighborhood");
			throw new ResourceNotFoundException("No record found for this neighborhood");
		}
		
		listEmployees.forEach(employee -> employee = Cryptography.decode(employee));
		
		LoggerConfig.LOGGER_EMPLOYEE.info("List of employees by neighborhood");

		return listEmployees;
	}

	@Override
	public ResponseEntity<Object> create(RequestEmployeeDTO requestEmployeeDTO) {
		Set<ConstraintViolation<RequestEmployeeDTO>> violations = validator.validate(requestEmployeeDTO);
		
		if(!violations.isEmpty()) {
			LoggerConfig.LOGGER_EMPLOYEE.error("Validation error");
			return new ResponseEntity<Object>(ResponseError.createFromValidation(violations), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		employee = mapper.toModel(requestEmployeeDTO);
		employee = Cryptography.encode(employee);
		
		String message = duplicateDocumentValidator(employee);
		
		if(!message.isEmpty()) {
			LoggerConfig.LOGGER_EMPLOYEE.error("Duplicate documents");
			throw new DuplicateDocumentsException(message);
		}
		
		repository.save(employee);

		responseEmployeeDTO = mapper.modelToResponseEmployeeDTO(employee);

		LoggerConfig.LOGGER_EMPLOYEE.info("Employee saved");

		return new ResponseEntity<Object>(responseEmployeeDTO, HttpStatus.CREATED) ;

	}

	@Override
	public ResponseEntity<Object> update(RequestEmployeeDTO requestEmployeeDTO, UUID employeeId) {
		Set<ConstraintViolation<RequestEmployeeDTO>> violations = validator.validate(requestEmployeeDTO);
		
		if(!violations.isEmpty()) {
			LoggerConfig.LOGGER_EMPLOYEE.error("Validation error");
			return new ResponseEntity<Object>(ResponseError.createFromValidation(violations), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		employee = repository.findById(employeeId).orElseThrow(() -> 
		 new ResourceNotFoundException("No records found for this id"));

		employee = UpdateModel.employee(employee, requestEmployeeDTO);
		employee = Cryptography.encode(employee);
		
		repository.save(employee);

		responseEmployeeDTO = mapper.modelToResponseEmployeeDTO(employee);
		
		LoggerConfig.LOGGER_EMPLOYEE.info("Employee data successfully updated");

		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

	@Override
	public Employee delete(UUID employeeId) {
		employee = repository.findById(employeeId).orElseThrow(() -> 
		 new ResourceNotFoundException("No records found for this id"));
		
		employee = mapper.employeeDelete(employee);

		repository.save(employee);

		LoggerConfig.LOGGER_EMPLOYEE.info("Successfully deleted employee");
		
		return employee;
	}
	
	private String duplicateDocumentValidator(Employee employee) {
		String message = "";

		Employee employeeEntityRg =  repository.findByRg(employee.getRg()); 	
		Employee employeeEntityCpf =  repository.findByCpf(employee.getCpf()); 
		
		if(employeeEntityCpf != null && employeeEntityRg != null) {
			message = "Documents already registered";
		}
		else if(employeeEntityCpf != null) {
			message = "Cpf already registered";
		}
		else if( employeeEntityRg != null){
			message = "Rg already registered";
		}		
		
		return message;
	}

}