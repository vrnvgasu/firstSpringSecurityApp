package ru.edu.FirstSecurityApp.security;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.edu.FirstSecurityApp.models.Person;

// Класс обертка users для работы с авторизацией
public class PersonDetails implements UserDetails {

	// класс с пользователями
	private final Person person;

	public PersonDetails(Person person) {
		this.person = person;
	}

	// получаем роли и действия пользователя
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// передаем строку роли или действия (пока храним только 1 роль)
		return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
	}

	@Override
	public String getPassword() {
		return this.person.getPassword();
	}

	@Override
	public String getUsername() {
		//собственно тут можем возвращать login, email и тд
		return this.person.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// учетка действительная (не просрочена по времени)
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// учетка действительная (не заблокирована)
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// пароль не просрочен
		return true;
	}

	@Override
	public boolean isEnabled() {
		// учетка включена
		return true;
	}

	// для получения данных аутентифицированного пользователя
	public Person getPerson() {
		return person;
	}

}
