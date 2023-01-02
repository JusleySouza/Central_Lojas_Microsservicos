package com.central.stores.employees.services;

import java.util.UUID;

import com.central.stores.employees.model.dto.AddressDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AddressServices {
	public ResponseEntity<Object> create(AddressDTO requestAddressDTO, UUID employeeId);
	public ResponseEntity<Object> update(AddressDTO requestAddressDTO, UUID addressId);
}
