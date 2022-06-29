package com.mttbrt.digres.dto.response.item;

import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserResDTO {

  @NotNull
  private String username;
  @NotNull
  private Set<@NotBlank @Size(min = 4, max = 32) String> roles;

  public UserResDTO() {
  }

  public UserResDTO(String username, Set<@NotBlank @Size(min = 4, max = 32) String> roles) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    UserResDTO that = (UserResDTO) o;

    return Objects.equals(username, that.getUsername())
        && Objects.equals(roles, that.getRoles());
  }
}
