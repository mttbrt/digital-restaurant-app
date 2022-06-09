package com.mttbrt.digres.utils;

import com.mttbrt.digres.dto.response.ErrorDTO;
import com.mttbrt.digres.dto.response.ResponseDTO;
import com.mttbrt.digres.dto.response.SingleErrorDTO;
import java.util.List;
import org.springframework.http.HttpStatus;

public class PreprocessingHelper {

  public static ResponseDTO createError(int code, String message, String errorMessage,
      String errorLocation) {
    SingleErrorDTO singleError = new SingleErrorDTO(errorMessage, errorLocation);
    ErrorDTO error = new ErrorDTO(code, message, List.of(singleError));
    return new ResponseDTO(error);
  }

  public static <T> ResponseDTO castRequest(T obj, Class<T> expectedClass, String location) {
    if (!expectedClass.isInstance(obj)) {
      return createError(HttpStatus.BAD_REQUEST.value(), "Request not valid.",
          "The request is not valid.", location);
    }
    return null;
  }

}
