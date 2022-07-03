package com.mttbrt.digres.dto.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Objects;
import javax.validation.constraints.NotBlank;

@JsonInclude(Include.NON_NULL)
public class SingleErrorResDTO {

  @NotBlank
  private String message;
  private String location;

  public SingleErrorResDTO() {
  }

  public SingleErrorResDTO(String message) {
    this(message, null);
  }

  public SingleErrorResDTO(String message, String location) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    SingleErrorResDTO that = (SingleErrorResDTO) o;

    return Objects.equals(message, that.getMessage())
        && Objects.equals(location, that.getLocation());
  }

  @Override
  public String toString() {
    return "SingleErrorResDTO{" +
        "message='" + message + '\'' +
        ", location='" + location + '\'' +
        '}';
  }
}
