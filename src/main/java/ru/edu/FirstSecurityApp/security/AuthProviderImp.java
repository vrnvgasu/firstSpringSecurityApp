package ru.edu.FirstSecurityApp.security;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.edu.FirstSecurityApp.services.PersonDetailsService;

@Component
public class AuthProviderImp implements AuthenticationProvider {

	private final PersonDetailsService personDetailsService;

	@Autowired
	public AuthProviderImp(PersonDetailsService personDetailsService) {
		this.personDetailsService = personDetailsService;
	}

	// сюда попадают данные из формы
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// authentication - объект из формы с логином/паролем
		String username = authentication.getName();
		UserDetails personDetails = personDetailsService.loadUserByUsername(username);

		String password = authentication.getCredentials().toString();
		if (!password.equals(personDetails.getPassword())) {
			throw new BadCredentialsException("Incorrect password");
		}

		// 3й параметр - список прав. Пока нет реализации, даем пустой список
		return new UsernamePasswordAuthenticationToken(personDetails, password, Collections.emptyList());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// для каких сценариев подход. Сейчас один провайдер, подходит для всего
		return true;
	}

}
