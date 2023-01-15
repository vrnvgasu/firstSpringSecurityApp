package ru.edu.FirstSecurityApp.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PersonDTO {

  @NotEmpty(message = "Имя не должно быть пустым")
  @Size(min = 2, max = 100, message = "от 2 до 100")
  private String username;

  @Min(value = 1900, message = "от 1900")
  private Integer yearOfBirth;

  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getYearOfBirth() {
    return yearOfBirth;
  }

  public void setYearOfBirth(Integer yearOfBirth) {
    this.yearOfBirth = yearOfBirth;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
