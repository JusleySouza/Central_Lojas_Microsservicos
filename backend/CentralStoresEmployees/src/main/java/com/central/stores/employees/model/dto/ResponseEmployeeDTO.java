package com.central.stores.employees.model.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResponseEmployeeDTO {
	
	private UUID id;
	private String name;
}
