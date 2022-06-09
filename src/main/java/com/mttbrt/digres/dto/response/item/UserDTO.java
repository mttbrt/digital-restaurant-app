package com.mttbrt.digres.dto.response.item;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDTO {

  @NotNull
  private String username;
  @NotNull
  private Set<@NotBlank @Size(min = 4, max = 32) String> roles;

  public UserDTO() {
  }

  public UserDTO(String username, Set<@NotBlank @Size(min = 4, max = 32) String> roles) {
    this.username = username;
    this.roles = roles;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Set<String> getRoles() {
    return roles;
  }

  public void setRoles(Set<String> roles) {
    this.roles = roles;
  }
}
