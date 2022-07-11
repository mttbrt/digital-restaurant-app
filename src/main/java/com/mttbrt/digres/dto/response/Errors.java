package com.mttbrt.digres.dto.response;

public enum Errors {
  USER_NOT_FOUND("User does not exist.", "No account with the given id exists."),
  USER_ALREDY_PRESENT("User already exists.", "An account with the given username already exists.");

  public final String message;
  public final String error;

  Errors(String message, String error) {
    this.message = message;
    this.error = error;
  }

}
