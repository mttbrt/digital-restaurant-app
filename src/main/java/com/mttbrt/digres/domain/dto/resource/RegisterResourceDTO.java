package com.mttbrt.digres.domain.dto.resource;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mttbrt.digres.domain.dto.resource.attribute.RegisterDTO;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@JsonTypeName("registration")
public class RegisterResourceDTO extends IResource {

  @Valid
  @NotNull
  private RegisterDTO attributes;

  public RegisterResourceDTO() {
    super();
  }

  public RegisterResourceDTO(String id, String type, RegisterDTO attributes) {
    super(id, type);
    this.attributes = attributes;
  }

  public RegisterDTO getAttributes() {
    return attributes;
  }

  public void setAttributes(RegisterDTO attributes) {
    this.attributes = attributes;
  }
}
