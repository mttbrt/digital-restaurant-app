package com.mttbrt.digres.dto.response.error;

import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ErrorResDTO {

  @NotBlank
  private int code;
  @NotBlank
  private String message;
  @NotNull
  private List<@Valid SingleErrorResDTO> errors;

  public ErrorResDTO() {
  }

  public ErrorResDTO(int code, String message, List<@Valid SingleErrorResDTO> errors) {
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

  public List<SingleErrorResDTO> getErrors() {
    return errors;
  }

  public void setErrors(List<SingleErrorResDTO> errors) {
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

    ErrorResDTO that = (ErrorResDTO) o;

    return Objects.equals(code, that.getCode())
        && Objects.equals(message, that.getMessage())
        && Objects.equals(errors, that.getErrors());
  }

  @Override
  public String toString() {
    return "ErrorResDTO{" +
        "code=" + code +
        ", message='" + message + '\'' +
        ", errors=" + errors +
        '}';
  }
}
