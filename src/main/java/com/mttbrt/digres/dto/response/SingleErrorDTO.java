package com.mttbrt.digres.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import javax.validation.constraints.NotBlank;

@JsonInclude(Include.NON_NULL)
public class SingleErrorDTO {

  @NotBlank
  private String message;
  private String location;

  public SingleErrorDTO() {
  }

  public SingleErrorDTO(String message) {
    this(message, null);
  }

  public SingleErrorDTO(String message, String location) {
    this.message = message;
    this.location = location;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
