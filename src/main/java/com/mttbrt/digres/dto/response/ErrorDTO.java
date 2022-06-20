package com.mttbrt.digres.dto.response;

import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ErrorDTO {

  @NotBlank(message = "cannot be blank.")
  private int code;
  @NotBlank(message = "cannot be blank.")
  private String message;
  @NotNull(message = "cannot be null.")
  private List<@Valid SingleErrorDTO> errors;

  public ErrorDTO() {
  }

  public ErrorDTO(int code, String message, List<@Valid SingleErrorDTO> errors) {
    this.code = code;
    this.message = message;
    this.errors = errors;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public List<SingleErrorDTO> getErrors() {
    return errors;
  }

  public void setErrors(List<SingleErrorDTO> errors) {
    this.errors = errors;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ErrorDTO that = (ErrorDTO) o;

    return Objects.equals(code, that.getCode())
        && Objects.equals(message, that.getMessage())
        && Objects.equals(errors, that.getErrors());
  }
}
