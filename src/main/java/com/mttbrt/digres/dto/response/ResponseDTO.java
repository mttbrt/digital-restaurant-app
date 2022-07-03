package com.mttbrt.digres.dto.response;

import static com.mttbrt.digres.utils.StaticVariables.API_VERSION;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mttbrt.digres.dto.response.data.item.IItem;
import com.mttbrt.digres.dto.response.error.ErrorResDTO;
import com.mttbrt.digres.dto.response.data.validation.ValidResponseDTO;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@ValidResponseDTO
@JsonInclude(Include.NON_NULL)
public class ResponseDTO {

  @NotBlank
  private String apiVersion;
  @Valid
  private IItem data;
  @Valid
  private ErrorResDTO error;

  public ResponseDTO() {
  }

  public ResponseDTO(IItem data) {
    this(API_VERSION, data, null);
  }

  public ResponseDTO(ErrorResDTO error) {
    this(API_VERSION, null, error);
  }

  public ResponseDTO(String apiVersion, IItem data, ErrorResDTO error) {
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

  public ErrorResDTO getError() {
    return error;
  }

  public void setError(ErrorResDTO error) {
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

  @Override
  public String toString() {
    return "ResponseDTO{" +
        "apiVersion='" + apiVersion + '\'' +
        ", data=" + data +
        ", error=" + error +
        '}';
  }
}
