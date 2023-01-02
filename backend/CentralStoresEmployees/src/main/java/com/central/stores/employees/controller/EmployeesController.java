package com.central.stores.employees.controller;

import java.util.List;
import java.util.UUID;

import com.central.stores.employees.model.Employee;
import com.central.stores.employees.model.dto.RequestEmployeeDTO;
import com.central.stores.employees.model.dto.ResponseEmployeeDTO;
import com.central.stores.employees.services.EmployeesServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees")
public class EmployeesController {
	@Autowired
	private EmployeesServices services;
	
	@PostMapping
	public ResponseEntity<Object> create(@RequestBody RequestEmployeeDTO requestEmployeeDTO) {
		return services.create(requestEmployeeDTO);
	}

	@PutMapping("/{employeeId}")
	public ResponseEntity<Object> update(@RequestBody RequestEmployeeDTO requestEmployeeDTO,
			@PathVariable("employeeId") UUID employeeId) {
		return services.update(requestEmployeeDTO, employeeId);
	}
	
	@DeleteMapping("/{employeeId}")
	public ResponseEntity<Object> delete(@PathVariable("employeeId") UUID employeeId){
		 services.delete(employeeId);
		 return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("list")
	public ResponseEntity<List<Employee>> listEmployees(){
		return new ResponseEntity<List<Employee>>(services.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{employeeCPF}")
	public ResponseEntity<Employee> findByCPF(@PathVariable("employeeCPF") final String employeeCpf){
		return new ResponseEntity<Employee>(services.findByCpf(employeeCpf),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<Employee>> findByNeighborhood(@RequestParam("neighborhood") String neighborhood){
		return new ResponseEntity<List<Employee>>(services.findByNeighborhood(neighborhood), HttpStatus.OK);
	}
}
