package ru.edu.FirstSecurityApp.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

	// в SecurityConfig указали возможность ограничения по роли в любом месте кода
	// с помощью @EnableGlobalMethodSecurity(prePostEnabled = true)
	// Можно делать более сложные конструкции ("hasRole('ROLE_ADMIN') or hasRole('ROLE_SOME')")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void doAdmin() {
		System.out.println("Can do only admin");
	}

}
