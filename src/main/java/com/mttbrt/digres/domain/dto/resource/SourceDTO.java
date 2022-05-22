package com.mttbrt.digres.domain.dto.resource;

import javax.validation.constraints.NotBlank;

public class SourceDTO {

  @NotBlank
  private String pointer;

  public SourceDTO() {
  }

  public SourceDTO(String pointer) {
    this.pointer = pointer;
  }

  public String getPointer() {
    return pointer;
  }

  public void setPointer(String pointer) {
    this.pointer = pointer;
  }
}
