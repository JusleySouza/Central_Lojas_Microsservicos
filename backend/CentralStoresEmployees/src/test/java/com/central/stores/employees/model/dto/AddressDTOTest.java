package com.central.stores.employees.model.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.central.stores.employees.test.utils.ClassBuilder;

public class AddressDTOTest {

	private AddressDTO expectedAddressDTO;
	
	@BeforeEach
	void setUp() {
		expectedAddressDTO = ClassBuilder.addressDTOBuilder();
	}
	
	@Test
	void setter() {
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setNumber(333);
		addressDTO.setCity("Teste city");
		addressDTO.setStreet("Teste rua");
		addressDTO.setNeighborhood("Teste bairro");
		
		assertEquals(expectedAddressDTO.toString(), addressDTO.toString());
	}
}
