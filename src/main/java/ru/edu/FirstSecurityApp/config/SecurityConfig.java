package ru.edu.FirstSecurityApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.edu.FirstSecurityApp.security.AuthProviderImp;

@EnableWebSecurity	// конфиг для Spring Security
// в version 3.0.0 WebSecurityConfigurerAdapter - вырезали
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// своя реализация провайдера для аутентификации
	private final AuthProviderImp authProvider;

	@Autowired
	public SecurityConfig(AuthProviderImp authProvider) {
		this.authProvider = authProvider;
	}

	// настраивает аутентификацию
	@Override
	public void configure(AuthenticationManagerBuilder auth) {
		auth.authenticationProvider(authProvider);
	}

}
