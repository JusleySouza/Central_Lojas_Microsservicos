package com.central.stores.employees.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AddressDTO {
	@NotEmpty(message = "{street.not.empty}")
	private String street;
	@NotNull(message = "{number.not.null}")
	@Min(value=1,message = "{number.not.less.than}")
	private Integer number;
	@NotEmpty(message = "{neighborhood.not.empty}")
	private String neighborhood;
	@NotEmpty(message = "{city.not.empty}")
	private String city;
}