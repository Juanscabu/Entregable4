package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Person {
	@Id
	private long dni;
	@Column
	private String name;
	@Column
	private String surname ;
	
	public Person() {
}

	public Person(long dni, String name, String surname) {
		this.dni = dni;
		this.name = name;
		this.surname = surname;
	}
}
	
	