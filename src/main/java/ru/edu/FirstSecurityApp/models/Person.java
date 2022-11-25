package ru.edu.FirstSecurityApp.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "person")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotEmpty(message = "Имя не должно быть пустым")
	@Size(min = 2, max = 100, message = "от 2 до 100")
	@Column(name = "username")
	private String username;

	@Min(value = 1900, message = "от 1900")
	@Column(name = "year_of_birth")
	private Integer yearOfBirth;

	@Column(name = "password")
	private String password;

	public Person(String username, Integer yearOfBirth) {
		this.username = username;
		this.yearOfBirth = yearOfBirth;
	}

	public Person() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getYearOfBirth() {
		return yearOfBirth;
	}

	public void setYearOfBirth(Integer yearOfBirth) {
		this.yearOfBirth = yearOfBirth;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Person{" +
				"id=" + id +
				", username='" + username + '\'' +
				", yearOfBirth=" + yearOfBirth +
				", password=" + password +
				'}';
	}

}
