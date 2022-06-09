package com.mttbrt.digres.dto.response.item;

import java.util.List;
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
}
