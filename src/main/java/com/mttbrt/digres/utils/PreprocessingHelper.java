package com.mttbrt.digres.utils;

import static com.mttbrt.digres.domain.ErrorCode.REQUEST_ERROR;

import com.mttbrt.digres.domain.ErrorCode;
import com.mttbrt.digres.domain.dto.DataDTO;
import com.mttbrt.digres.domain.dto.ErrorsDTO;
import com.mttbrt.digres.domain.dto.ResponseDTO;
import com.mttbrt.digres.domain.dto.resource.ErrorDTO;
import com.mttbrt.digres.domain.dto.resource.IResource;
import com.mttbrt.digres.domain.dto.resource.SourceDTO;
import java.util.Collections;
import org.springframework.http.HttpStatus;

public class PreprocessingHelper {

  public static ErrorsDTO createError(int status, ErrorCode code, String title, String details,
      String source) {
    ErrorDTO error = new ErrorDTO(status, code, title, details, new SourceDTO(source));
    return new ErrorsDTO(Collections.singletonList(error));
  }

  public static ResponseDTO castRequest(String source, DataDTO obj, Class<?> expectedClass) {
    IResource resource = obj.getData();
    if (!expectedClass.isInstance(resource)) {
      return createError(HttpStatus.BAD_REQUEST.value(), REQUEST_ERROR, "Request not valid.",
          "The request is not valid.", source);
    }
    return null;
  }

}
