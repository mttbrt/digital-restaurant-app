package com.mttbrt.digres.dto.response;

import static com.mttbrt.digres.utils.StaticVariables.API_VERSION;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mttbrt.digres.dto.response.item.IItem;
import com.mttbrt.digres.dto.response.validation.ValidResponseDTO;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@ValidResponseDTO
@JsonInclude(Include.NON_NULL)
public class ResponseDTO {

  @NotBlank(message = "cannot be blank.")
  private String apiVersion;
  @Valid
  private IItem data;
  @Valid
  private ErrorDTO error;

  public ResponseDTO() {
  }

  public ResponseDTO(IItem data) {
    this(API_VERSION, data, null);
  }

  public ResponseDTO(ErrorDTO error) {
    this(API_VERSION, null, error);
  }

  public ResponseDTO(String apiVersion, IItem data, ErrorDTO error) {
    this.apiVersion = apiVersion;
    this.data = data;
    this.error = error;
  }

  public String getApiVersion() {
    return apiVersion;
  }

  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  public IItem getData() {
    return data;
  }

  public void setData(IItem data) {
    this.data = data;
  }

  public ErrorDTO getError() {
    return error;
  }

  public void setError(ErrorDTO error) {
    this.error = error;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ResponseDTO that = (ResponseDTO) o;

    return Objects.equals(apiVersion, that.getApiVersion())
        && Objects.equals(data, that.getData())
        && Objects.equals(error, that.getError());
  }

}
