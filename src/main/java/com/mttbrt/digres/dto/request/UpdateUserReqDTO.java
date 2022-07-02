package com.mttbrt.digres.dto.request;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UpdateUserReqDTO {

  @NotBlank
  @Size(min = 8, max = 32, message = "The length must be between 8 and 32 characters.")
  private String password;
  @NotNull
  @Size(min = 1, message = "At least one role must be provided.")
  private Set<@NotBlank @Size(min = 4, max = 32) String> roles;

  public UpdateUserReqDTO() {

  }

  public UpdateUserReqDTO(String password,
      Set<@NotBlank @Size(min = 4, max = 32) String> roles) {
    this.password = password;
    this.roles = roles;
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

  @Override
  public String toString() {
    return "AddUserDTO{" +
        "password='" + password + '\'' +
        ", roles=" + roles +
        '}';
  }
}
