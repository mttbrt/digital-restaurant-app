package com.mttbrt.digres.utils;

import com.mttbrt.digres.dto.response.error.ErrorResDTO;
import com.mttbrt.digres.dto.response.ResponseDTO;
import com.mttbrt.digres.dto.response.error.SingleErrorResDTO;
import java.util.List;
import org.springframework.http.HttpStatus;

public class PreprocessingHelper {

  public static ResponseDTO createError(int code, String message, String errorMessage,
      String errorLocation) {
    SingleErrorResDTO singleError = new SingleErrorResDTO(errorMessage, errorLocation);
    ErrorResDTO error = new ErrorResDTO(code, message, List.of(singleError));
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
