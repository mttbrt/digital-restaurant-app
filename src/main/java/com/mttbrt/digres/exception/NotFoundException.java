package com.mttbrt.digres.exception;

import com.mttbrt.digres.dto.response.Errors;

public class NotFoundException extends LookupException {

  public NotFoundException(Errors err, String location) {
    super(err, location);
  }
}
