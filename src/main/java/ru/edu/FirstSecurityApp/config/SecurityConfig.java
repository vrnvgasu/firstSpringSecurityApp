package ru.edu.FirstSecurityApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.edu.FirstSecurityApp.services.PersonDetailsService;

@EnableWebSecurity  // конфиг для Spring Security
// в version 3.0.0 WebSecurityConfigurerAdapter - вырезали
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// своя реализация провайдера для аутентификации
//	private final AuthProviderImp authProvider;
//	@Autowired
//	public SecurityConfig(AuthProviderImp authProvider) {
//		this.authProvider = authProvider;
//	}

	// если своя реалзиация провайдера не нужна, то сразу передаем сервис для Spring Security
	// он с ним разберется самостоятельно
	private final PersonDetailsService personDetailsService;

	@Autowired
	public SecurityConfig(PersonDetailsService personDetailsService) {
		this.personDetailsService = personDetailsService;
	}

	// настраивает аутентификацию
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(authProvider); //своя реализация провайдера для аутентификации
		auth.userDetailsService(personDetailsService); // тут spring сам разберется с аутентификацией
	}

	// при работе без провайдера надо указать бин с типом шифрования пароля
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		// пока не шифруем
		return NoOpPasswordEncoder.getInstance();
	}

	// конфигурируем сам Spring Security: какая страница отвечает за вход, за ошибку и тд
	// тут также идет управление авторизацией. Кто что может делать
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http

				// пока не передаем csrf токен с фронта
				// отключаем защиту от межсайтовой подделки запросов
				.csrf().disable()

				// настройка прав на просмотр ендпоинтов
				.authorizeRequests()
				.antMatchers("/auth/login", "/error").permitAll() // на страницу аутентификации и ошибки пускам всех
				.anyRequest().authenticated() // на все остальное пускаем только аутентифицированных

				.and() // соединяет разные части настроек

				// процесс аутентификации
				.formLogin().loginPage("/auth/login") // настраиваем форму для входа (ендпоинт)
				.loginProcessingUrl("/process_login")  // ендпоинт, куда придет логин и пароль. Его даже реализовывать не надо
				.defaultSuccessUrl("/hello", true) // перенаправление после успешной аутентификации
				.failureUrl("/auth/login?error "); // перенаправление при ошибке аутентификации
	}

}
