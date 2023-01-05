package com.central.stores.employees.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.central.stores.employees.test.utils.ClassBuilder;

public class AddressTest {

	private Address expectedAddress;
	
	private UUID id;

	@BeforeEach
	void setUp() {
		id = UUID.randomUUID();
		expectedAddress = ClassBuilder.addressBuilder();
		expectedAddress.setId(id);
	}
	
	@Test
	void setter() {
		Address address = new Address();
		address.setId(id);
		address.setNumber(333);
		address.setCity("Teste city");
		address.setStreet("Teste rua");
		address.setChanged(LocalDate.now());
		address.setCreated(LocalDate.now());
		address.setNeighborhood("Teste bairro");
		
		assertEquals(expectedAddress.toString(), address.toString());
	}
}
