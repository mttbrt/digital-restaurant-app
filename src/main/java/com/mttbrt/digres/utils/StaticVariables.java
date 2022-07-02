package com.mttbrt.digres.utils;

public class StaticVariables {

  public static final String API_VERSION = "v1";
  public static final String API_NAMESPACE = "/api/" + API_VERSION;
  public static final String AUTH_NAMESPACE = API_NAMESPACE + "/auth";
  public static final String USERS_ENDPOINT = API_NAMESPACE + "/users";
  public static final String LOGIN_ENDPOINT = AUTH_NAMESPACE + "/login";
  public static final String LOGOUT_ENDPOINT = AUTH_NAMESPACE + "/logout";
  public static final String AUTHORITY_PREFIX = "ROLE_";

}
