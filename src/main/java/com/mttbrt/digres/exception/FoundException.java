package com.mttbrt.digres.exception;

import com.mttbrt.digres.dto.response.Errors;

public class FoundException extends LookupException {

  public FoundException(Errors err, String location) {
    super(err, location);
  }
}
