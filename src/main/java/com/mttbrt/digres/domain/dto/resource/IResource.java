package com.mttbrt.digres.domain.dto.resource;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import javax.validation.constraints.NotBlank;

@JsonTypeInfo(use = Id.NAME, property = "type", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = LoginResourceDTO.class),
    @JsonSubTypes.Type(value = RegisterResourceDTO.class)
})
public abstract class IResource {

  private String id;
  @NotBlank
  private String type;

  public IResource() {
  }

  public IResource(String id, String type) {
    this.id = id;
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
