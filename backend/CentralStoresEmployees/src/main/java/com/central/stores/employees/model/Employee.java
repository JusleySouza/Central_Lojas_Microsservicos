package com.central.stores.employees.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.central.stores.employees.constants.Conf;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Employee implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private UUID id;
	@Column(nullable = false, unique = true)
	private String cpf;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String rg;
	@Column(nullable = false)
	private String role;
	@Column(nullable = false)
	private String gender;
	@Column(nullable = false)
	private Boolean active;
	@Column(nullable = false)
	private String phone;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	@DateTimeFormat(pattern = Conf.DATE_FORMAT)
	private LocalDate created;
	@DateTimeFormat(pattern = Conf.DATE_FORMAT)
	private LocalDate changed;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "addressId")
	private Address address;
}
