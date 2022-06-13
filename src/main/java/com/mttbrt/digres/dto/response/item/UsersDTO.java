package com.mttbrt.digres.dto.response.item;

import com.mttbrt.digres.dto.response.ResponseDTO;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class UsersDTO implements IItem {

  @NotNull
  private List<@Valid UserDTO> users;

  public UsersDTO() {
  }

  public UsersDTO(List<@Valid UserDTO> users) {
    this.users = users;
  }

  public List<UserDTO> getUsers() {
    return users;
  }

  public void setUsers(List<UserDTO> users) {
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

    UsersDTO that = (UsersDTO) o;

    return Objects.equals(users, that.getUsers());
  }
}
