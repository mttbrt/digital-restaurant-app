package com.mttbrt.digres.domain.dto;

import com.mttbrt.digres.domain.dto.resource.IResource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class DataDTO {

  @Valid
  @NotNull
  private IResource data;

  public DataDTO() {
  }

  public DataDTO(IResource data) {
    this.data = data;
  }

  public IResource getData() {
    return data;
  }

  public void setData(IResource data) {
    this.data = data;
  }
}
