package com.mttbrt.digres.domain.dto;

import javax.validation.constraints.NotNull;

public class PositiveResponseDTO implements ResponseDTO {

  @NotNull
  private String tbd;

  public PositiveResponseDTO() {
  }

  public PositiveResponseDTO(String tbd) {
    this.tbd = tbd;
  }

  public String getTbd() {
    return tbd;
  }

  public void setTbd(String tbd) {
    this.tbd = tbd;
  }
}
