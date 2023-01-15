package ru.edu.FirstSecurityApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.edu.FirstSecurityApp.security.PersonDetails;
import ru.edu.FirstSecurityApp.services.AdminService;

@Controller
public class HelloController {

	private final AdminService adminService;

	@Autowired
	public HelloController(AdminService adminService) {
		this.adminService = adminService;
	}

	@GetMapping("/hello")
	public String sayHello() {
		return "hello";
	}

	@GetMapping("showUserInfo")
	@ResponseBody // для rest
	public String showUserInfo() {
		// объект аутентифицированного пользователя можно получить в любом месте из сессии
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// у нас объект аутентификации возвращает свою реализацию
		PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

		System.out.println(personDetails.getPerson());

		return personDetails.getUsername();
	}

	@GetMapping("/admin")
	public String adminPage() {
		adminService.doAdmin();
		return "admin";
	}

}
