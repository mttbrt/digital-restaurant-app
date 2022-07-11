package com.mttbrt.digres.utils;

import com.mttbrt.digres.domain.User;
import com.mttbrt.digres.dto.response.data.item.UserResDTO;

public class ConvertUserToResDTO {

  public static UserResDTO convertToDTO(User user) {
    return new UserResDTO(user.getUsername(), user.getRoles());
  }

}
