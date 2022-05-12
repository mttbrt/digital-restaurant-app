package com.digital.domain.dto;

import com.digital.domain.dto.content.ErrorDTO;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ErrorsDTO {

  @NotNull
  @Size(min = 1, message = "Please return at least 1 error.")
  private List<ErrorDTO> errors;

  public ErrorsDTO() {
  }

  public ErrorsDTO(List<ErrorDTO> errors) {
    this.errors = errors;
  }

  public List<ErrorDTO> getErrors() {
    return errors;
  }

  public void setErrors(List<ErrorDTO> errors) {
    this.errors = errors;
  }
}
