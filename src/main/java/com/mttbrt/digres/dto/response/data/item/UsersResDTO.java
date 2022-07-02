package com.mttbrt.digres.dto.response.data.item;

import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class UsersResDTO implements IItem {

  @NotNull
  private List<@Valid UserResDTO> users;

  public UsersResDTO() {
  }

  public UsersResDTO(List<@Valid UserResDTO> users) {
    this.users = users;
  }

  public List<UserResDTO> getUsers() {
    return users;
  }

  public void setUsers(List<UserResDTO> users) {
    this.users = users;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    UsersResDTO that = (UsersResDTO) o;

    return Objects.equals(users, that.getUsers());
  }
}
