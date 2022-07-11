package com.mttbrt.digres.exception;

import com.mttbrt.digres.dto.response.Errors;

public class LookupException extends Exception {

  private final Errors err;
  private final String location;

  public LookupException(Errors err, String location) {
    this.err = err;
    this.location = location;
  }

  public Errors getErr() {
    return err;
  }

  public String getLocation() {
    return location;
  }
}
