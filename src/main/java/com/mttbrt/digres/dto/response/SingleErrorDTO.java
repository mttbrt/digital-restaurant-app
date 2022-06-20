package com.mttbrt.digres.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Objects;
import javax.validation.constraints.NotBlank;

@JsonInclude(Include.NON_NULL)
public class SingleErrorDTO {

  @NotBlank(message = "cannot be blank.")
  private String message;

  public SingleErrorDTO() {
  }

  public SingleErrorDTO(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    SingleErrorDTO that = (SingleErrorDTO) o;

    return Objects.equals(message, that.getMessage());
  }
}
