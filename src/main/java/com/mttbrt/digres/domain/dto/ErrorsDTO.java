package com.mttbrt.digres.domain.dto;

import com.mttbrt.digres.domain.dto.resource.ErrorDTO;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ErrorsDTO implements ResponseDTO {

  @NotNull
  @Size(min = 1, message = "Please return at least 1 error.")
  private List<@Valid ErrorDTO> errors;

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
