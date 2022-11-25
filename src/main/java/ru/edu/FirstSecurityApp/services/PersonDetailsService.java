package ru.edu.FirstSecurityApp.services;

import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.edu.FirstSecurityApp.models.Person;
import ru.edu.FirstSecurityApp.repositories.PeopleRepository;
import ru.edu.FirstSecurityApp.security.PersonDetails;

// этот сервис для Spring Security. Реализуем в нем UserDetailsService
@Service
public class PersonDetailsService implements UserDetailsService {

	private final PeopleRepository peopleRepository;

	public PersonDetailsService(PeopleRepository peopleRepository) {
		this.peopleRepository = peopleRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Person> personOptional =  peopleRepository.findByUsername(username);

		if (personOptional.isEmpty()) {
			throw new UsernameNotFoundException("User not found!");
		}

		// обертка PersonDetails реализует UserDetails
		return new PersonDetails(personOptional.get());
	}

}
