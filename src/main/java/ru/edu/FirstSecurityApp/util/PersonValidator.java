package ru.edu.FirstSecurityApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.edu.FirstSecurityApp.models.Person;
import ru.edu.FirstSecurityApp.services.PersonDetailsService;

@Component
public class PersonValidator implements Validator {

	// переиспользуем этот сервис для ускорения разработки. По хорошему нужен отдельный
	private final PersonDetailsService personDetailsService;

	@Autowired
	public PersonValidator(PersonDetailsService personDetailsService) {
		this.personDetailsService = personDetailsService;
	}

	// валидация для объектов Person
	@Override
	public boolean supports(Class<?> clazz) {
		return Person.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Person person = (Person) target;

		try {
			personDetailsService.loadUserByUsername(person.getUsername());
		} catch (UsernameNotFoundException e) {
			return;
		}

		errors.rejectValue("username", "", "Человек с таким username существует");
	}

}
