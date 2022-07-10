package com.mttbrt.digres.dto.response;

import static com.mttbrt.digres.utils.StaticVariables.API_VERSION;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mttbrt.digres.dto.response.data.item.IItem;
import com.mttbrt.digres.dto.response.error.ErrorResDTO;
import com.mttbrt.digres.dto.response.data.validation.ValidResponseDTO;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
  @JsonIgnore
  private ZonedDateTime modified;

  public ResponseDTO() {
  }

  public ResponseDTO(IItem data, OffsetDateTime modified) {
    this(API_VERSION, data, null, modified);
  }

  public ResponseDTO(IItem data) {
    this(API_VERSION, data, null, null);
  }

  public ResponseDTO(ErrorResDTO error, OffsetDateTime modified) {
    this(API_VERSION, null, error, modified);
  }

  public ResponseDTO(ErrorResDTO error) {
    this(API_VERSION, null, error, null);
  }

  public ResponseDTO(String apiVersion, IItem data, ErrorResDTO error, OffsetDateTime modified) {
    this.apiVersion = apiVersion;
    this.data = data;
    this.error = error;
    setModified(modified);
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

  public ZonedDateTime getModified() {
    return modified;
  }

  public void setModified(OffsetDateTime modified) {
    this.modified = modified.atZoneSameInstant(ZoneId.of("Europe/Zurich"));
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
