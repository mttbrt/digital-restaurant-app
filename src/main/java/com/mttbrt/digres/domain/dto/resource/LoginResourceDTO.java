package com.mttbrt.digres.domain.dto.resource;

import com.mttbrt.digres.domain.dto.resource.attribute.LoginDTO;
import com.fasterxml.jackson.annotation.JsonTypeName;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JsonTypeName("login")
public class LoginResourceDTO extends IResource {

  @Valid
  @NotNull
  private LoginDTO attributes;

  public LoginResourceDTO() {
    super();
  }

  public LoginResourceDTO(String id, String type, LoginDTO attributes) {
    super(id, type);
    this.attributes = attributes;
  }

  public LoginDTO getAttributes() {
    return attributes;
  }

  public void setAttributes(LoginDTO attributes) {
    this.attributes = attributes;
  }
}
