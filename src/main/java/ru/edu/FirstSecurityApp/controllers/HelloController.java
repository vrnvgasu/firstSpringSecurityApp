package ru.edu.FirstSecurityApp.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.edu.FirstSecurityApp.security.PersonDetails;

@Controller
public class HelloController {

	@GetMapping("/hello")
	public String sayHello() {
		return "hello";
	}

	@GetMapping("showUserInfo")
	public String showUserInfo() {
		// объект аутентифицированного пользователя можно получить в любом месте из сессии
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// у нас объект аутентификации возвращает свою реализацию
		PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

		System.out.println(personDetails.getPerson());

		return "hello";
	}

}
