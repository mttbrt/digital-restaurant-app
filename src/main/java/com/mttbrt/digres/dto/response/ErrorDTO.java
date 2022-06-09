package com.mttbrt.digres.dto.response;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ErrorDTO {

  @NotBlank
  private int code;
  @NotBlank
  private String message;
  @NotNull
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
}
