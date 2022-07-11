package com.mttbrt.digres.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

public class BasicResDTO {

  @JsonIgnore
  private ZonedDateTime modified;
  @JsonIgnore
  private URI location;

  public BasicResDTO() {
  }

  public BasicResDTO(ZonedDateTime modified, String location) {
    setModified(modified);
    setLocation(location);
  }

  public BasicResDTO(OffsetDateTime modified, String location) {
    setModified(modified);
    setLocation(location);
  }

  public BasicResDTO(ZonedDateTime modified) {
    setModified(modified);
  }

  public BasicResDTO(OffsetDateTime modified) {
    setModified(modified);
  }

  public ZonedDateTime getModified() {
    return modified;
  }

  public void setModified(OffsetDateTime modified) {
    this.modified =
        modified != null ? modified.atZoneSameInstant(ZoneId.of("Europe/Zurich")) : null;
  }

  public void setModified(ZonedDateTime modified) {
    this.modified = modified;
  }

  public URI getLocation() {
    return location;
  }

  public void setLocation(String location) {
    try {
      this.location = location != null ? new URI(location) : null;
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    BasicResDTO that = (BasicResDTO) o;

    return Objects.equals(modified, that.getModified())
        && Objects.equals(location, that.getLocation());
  }

  @Override
  public String toString() {
    return "BasicResDTO{" +
        "modified=" + modified +
        ", location=" + location +
        '}';
  }
}
