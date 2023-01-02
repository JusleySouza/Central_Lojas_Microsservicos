package com.central.stores.employees.controller;

import java.util.UUID;

import com.central.stores.employees.model.dto.AddressDTO;
import com.central.stores.employees.services.AddressServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees/{employeeId}/address")
public class AddressController {
	@Autowired
	private AddressServices services;

	@PostMapping
	public ResponseEntity<Object> create(@RequestBody AddressDTO requestAddressDTO,
			@PathVariable("employeeId") UUID employeeId) {
		return services.create(requestAddressDTO, employeeId);
	}

	@PutMapping("/{addressId}")
	public ResponseEntity<Object> update(@RequestBody AddressDTO requestAddressDTO,
			@PathVariable("addressId") UUID addressId) {
		return services.update(requestAddressDTO, addressId);
	}
}
