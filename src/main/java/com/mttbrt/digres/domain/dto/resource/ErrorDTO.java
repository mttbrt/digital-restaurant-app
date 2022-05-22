package com.mttbrt.digres.domain.dto.resource;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

public class ErrorDTO {

  @NotBlank
  private int status;
  @NotBlank
  private String code;
  @NotBlank
  private String title;
  private String detail;
  @NotBlank
  @Valid
  private SourceDTO source;

  public ErrorDTO() {
  }

  public ErrorDTO(int status, String code, String title, String detail, SourceDTO source) {
    this.status = status;
    this.code = code;
    this.title = title;
    this.detail = detail;
    this.source = source;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDetail() {
    return detail;
  }

  public void setDetail(String detail) {
    this.detail = detail;
  }

  public SourceDTO getSource() {
    return source;
  }

  public void setSource(SourceDTO source) {
    this.source = source;
  }
}
