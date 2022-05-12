package com.digital.domain.dto.content.attribute;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class LoginReqDTO implements IAttributes {

  @NotBlank
  @Size(min = 4, max = 32, message = "The username length must be between 4 and 32 characters.")
  private String username;
  @NotBlank
  @Size(min = 8, max = 32, message = "The password length must be between 8 and 32 characters.")
  private String password;

  public LoginReqDTO() {
  }

  public LoginReqDTO(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
