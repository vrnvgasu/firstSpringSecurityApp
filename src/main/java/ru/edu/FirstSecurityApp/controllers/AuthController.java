package ru.edu.FirstSecurityApp.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.edu.FirstSecurityApp.models.Person;
import ru.edu.FirstSecurityApp.services.RegistrationService;
import ru.edu.FirstSecurityApp.util.PersonValidator;

@Controller
@RequestMapping("/auth")
public class AuthController {

	private final PersonValidator personValidator;

	private final RegistrationService registrationService;

	@Autowired
	public AuthController(PersonValidator personValidator, RegistrationService registrationService) {
		this.personValidator = personValidator;
		this.registrationService = registrationService;
	}

	@GetMapping("/login")
	public String loginPage() {
		return "auth/login";
	}

	// @ModelAttribute сразу добавит в модель для шаблонизатора пустой класс Person
	@GetMapping("/registration")
	public String registrationPage(@ModelAttribute("person") Person person) {
		return "auth/registration";
	}

	// ModelAttribute - сразу получили данные из формы и положили в объект person
	@PostMapping("/registration")
	public String performRegistration(@ModelAttribute("person") @Valid Person person,
			BindingResult bindingResult) {
		personValidator.validate(person, bindingResult);

		if (bindingResult.hasErrors()) {
			return "auth/registration";
		}

		registrationService.register(person);

		return "redirect:/auth/login";
	}

}
