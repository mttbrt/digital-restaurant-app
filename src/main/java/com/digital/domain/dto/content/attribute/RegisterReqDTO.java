package com.digital.domain.dto.content.attribute;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterReqDTO implements IAttributes {

  @NotBlank
  @Size(min = 4, max = 32, message = "The username length must be between 4 and 32 characters.")
  private String username;
  @NotBlank
  @Size(min = 8, max = 32, message = "The password length must be between 8 and 32 characters.")
  private String password;
  @NotNull
  @Size(min = 1, message = "At least one role must be provided.")
  private Set<@NotBlank @Size(min = 4, max = 32) String> roles;

  public RegisterReqDTO() {
  }

  public RegisterReqDTO(String username, String password,
      Set<@NotBlank @Size(min = 4, max = 32) String> roles) {
    this.username = username;
    this.password = password;
    this.roles = roles;
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

  public Set<String> getRoles() {
    return roles;
  }

  public void setRoles(Set<String> roles) {
    this.roles = roles;
  }
}
