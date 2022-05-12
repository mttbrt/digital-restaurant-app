package com.digital.domain.dto;

import com.digital.domain.dto.content.ResourceDTO;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DataDTO {

  @NotNull
  private ResourceDTO data;

  public DataDTO() {
  }

  public DataDTO(ResourceDTO data) {
    this.data = data;
  }

  public ResourceDTO getData() {
    return data;
  }

  public void setData(ResourceDTO data) {
    this.data = data;
  }
}
