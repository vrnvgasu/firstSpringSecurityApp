package ru.edu.FirstSecurityApp.services;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.edu.FirstSecurityApp.models.Person;
import ru.edu.FirstSecurityApp.repositories.PeopleRepository;

@Service
public class RegistrationService {

	private final PeopleRepository peopleRepository;

	private final PasswordEncoder passwordEncoder;

	@Autowired
	public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
		this.peopleRepository = peopleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void register(Person person) {
		String encodedPassword = passwordEncoder.encode(person.getPassword());
		person.setPassword(encodedPassword);
		person.setRole("ROLE_USER");
		peopleRepository.save(person);
	}

}
