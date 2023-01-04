package com.central.stores.employees.services.implement;

import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.central.stores.employees.config.LoggerConfig;
import com.central.stores.employees.exception.ResourceNotFoundException;
import com.central.stores.employees.mapper.AddressMapper;
import com.central.stores.employees.mapper.UpdateModel;
import com.central.stores.employees.model.Address;
import com.central.stores.employees.model.Employee;
import com.central.stores.employees.model.dto.AddressDTO;
import com.central.stores.employees.model.dto.error.ResponseError;
import com.central.stores.employees.repository.AddressRepository;
import com.central.stores.employees.repository.EmployeesRepository;
import com.central.stores.employees.services.AddressServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AddressServicesImplent implements AddressServices {
	@Autowired
	private AddressMapper mapper;
	
	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private EmployeesRepository employeesRepository;
	
	@Autowired
	private Validator validator;

	private Address address;
	
	private Employee employee;
	
	@Override
	public ResponseEntity<Object> create(AddressDTO requestAddressDTO, UUID employeeId) {
		Set<ConstraintViolation<AddressDTO>> violations = validator.validate(requestAddressDTO);
		
		if(!violations.isEmpty()) {
			return new ResponseEntity<Object>(ResponseError.createFromValidation(violations), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		address = mapper.toModel(requestAddressDTO);
		addressRepository.save(address);
		
		employee = employeesRepository.findById(employeeId).get();
		employee.setAddress(address);
		
		employeesRepository.save(employee);
		
		LoggerConfig.LOGGER_ADDRESS.info("Address saved");

		return new ResponseEntity<Object>(address, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Object> update(AddressDTO requestAddressDTO, UUID addressId) {
		Set<ConstraintViolation<AddressDTO>> violations = validator.validate(requestAddressDTO);
		
		if(!violations.isEmpty()) {
			return new ResponseEntity<Object>(ResponseError.createFromValidation(violations), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		address = addressRepository.findById(addressId).orElseThrow(() -> 
		 new ResourceNotFoundException("No records found for this id"));
		
		address =  UpdateModel.address(address, requestAddressDTO);
		
		addressRepository.save(address);
		
		LoggerConfig.LOGGER_ADDRESS.info("Address updated");
		
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}

}
