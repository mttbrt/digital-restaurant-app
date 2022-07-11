package com.mttbrt.digres.dto.response;

import static com.mttbrt.digres.utils.StaticVariables.API_VERSION;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mttbrt.digres.dto.response.data.item.IItem;
import com.mttbrt.digres.dto.response.data.validation.ValidResponseDTO;
import com.mttbrt.digres.dto.response.error.ErrorResDTO;
import java.time.OffsetDateTime;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@ValidResponseDTO
@JsonInclude(Include.NON_NULL)
public class ResponseDTO extends BasicResDTO {

  @NotBlank
  private String apiVersion;
  @Valid
  private IItem data;
  @Valid
  private ErrorResDTO error;

  public ResponseDTO() {
    super();
  }

  public ResponseDTO(OffsetDateTime modified) {
    super(modified, null);
  }

  public ResponseDTO(IItem data) {
    this(API_VERSION, data, null, null);
  }

  public ResponseDTO(ErrorResDTO error) {
    this(API_VERSION, null, error, null);
  }

  public ResponseDTO(IItem data, OffsetDateTime modified) {
    this(API_VERSION, data, null, modified);
  }

  public ResponseDTO(ErrorResDTO error, OffsetDateTime modified) {
    this(API_VERSION, null, error, modified);
  }

  public ResponseDTO(String apiVersion, IItem data, ErrorResDTO error, OffsetDateTime modified) {
    super(modified, null);
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
