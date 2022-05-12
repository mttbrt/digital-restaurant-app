package com.digital.domain.dto.content;

import javax.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;

public class ErrorDTO {

  @NotBlank
  private long id;
  private HttpStatus status;
  private String code;
  @NotBlank
  private String title;
  private String detail;
  @NotBlank
  private String source;

  public ErrorDTO() {
  }

  public ErrorDTO(
      long id,
      HttpStatus status,
      String code,
      String title,
      String detail,
      String source) {
    this.id = id;
    this.status = status;
    this.code = code;
    this.title = title;
    this.detail = detail;
    this.source = source;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(HttpStatus status) {
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

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }
}
