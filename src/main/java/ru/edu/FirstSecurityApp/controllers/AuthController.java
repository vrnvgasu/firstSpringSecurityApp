package ru.edu.FirstSecurityApp.controllers;

import java.util.Map;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.FirstSecurityApp.dto.AuthenticationDTO;
import ru.edu.FirstSecurityApp.dto.PersonDTO;
import ru.edu.FirstSecurityApp.models.Person;
import ru.edu.FirstSecurityApp.security.JWTUtil;
import ru.edu.FirstSecurityApp.services.RegistrationService;
import ru.edu.FirstSecurityApp.util.PersonValidator;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final PersonValidator personValidator;

  private final RegistrationService registrationService;

  private final AuthenticationManager authenticationManager;

  private final JWTUtil jwtUtil;

  private final ModelMapper modelMapper;

  @Autowired
  public AuthController(PersonValidator personValidator, RegistrationService registrationService,
      AuthenticationManager authenticationManager, JWTUtil jwtUtil,
      ModelMapper modelMapper) {
    this.personValidator = personValidator;
    this.registrationService = registrationService;
    this.authenticationManager = authenticationManager;
    this.jwtUtil = jwtUtil;
    this.modelMapper = modelMapper;
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
  public Map<String, String> performRegistration(@RequestBody @Valid PersonDTO personDTO,
      BindingResult bindingResult) {
    Person person = convertToPerson(personDTO);
    personValidator.validate(person, bindingResult);

    if (bindingResult.hasErrors()) {
      // по хорошему надо выкинуть ошибку и перехватить ее в exceptionHandler
      return Map.of("message", "Ошибка");
    }

    registrationService.register(person);
    String token = jwtUtil.generateToken(person.getUsername());

    return Map.of("jwt-token", token);
  }

  // обновить токен
  @PostMapping("/login")
  public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
    UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
        authenticationDTO.getUsername(),
        authenticationDTO.getPassword()
    );
    try {
      authenticationManager.authenticate(authInputToken);
    } catch (BadCredentialsException e) {
      return Map.of("message", "Incorrect credentials!");
    }

    String token = jwtUtil.generateToken(authenticationDTO.getUsername());
    return Map.of("jwt-token", token);
  }

  public Person convertToPerson(PersonDTO personDTO) {
    return modelMapper.map(personDTO, Person.class);
  }

}
