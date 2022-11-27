package ru.edu.FirstSecurityApp.services;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edu.FirstSecurityApp.models.Person;
import ru.edu.FirstSecurityApp.repositories.PeopleRepository;

@Service
public class RegistrationService {

	private final PeopleRepository peopleRepository;

	@Autowired
	public RegistrationService(PeopleRepository peopleRepository) {
		this.peopleRepository = peopleRepository;
	}

	@Transactional
	public void register(Person person) {
		peopleRepository.save(person);
	}

}
