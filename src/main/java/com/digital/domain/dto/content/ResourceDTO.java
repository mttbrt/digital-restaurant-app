package com.digital.domain.dto.content;

import com.digital.domain.dto.content.attribute.IAttributes;
import javax.validation.constraints.NotBlank;

public class ResourceDTO {

  private String id;
  @NotBlank
  private String type;
  @NotBlank
  private IAttributes attributes;

  public ResourceDTO() {
  }

  public ResourceDTO(String id, String type, IAttributes attributes) {
    this.id = id;
    this.type = type;
    this.attributes = attributes;
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

  public IAttributes getAttributes() {
    return attributes;
  }

  public void setAttributes(IAttributes attributes) {
    this.attributes = attributes;
  }
}
