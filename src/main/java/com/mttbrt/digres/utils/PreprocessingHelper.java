package com.mttbrt.digres.utils;

import static com.mttbrt.digres.domain.ErrorCode.JSON_VALIDATION_ERROR;
import static com.mttbrt.digres.domain.ErrorCode.REQUEST_ERROR;

import com.mttbrt.digres.domain.ErrorCode;
import com.mttbrt.digres.domain.dto.DataDTO;
import com.mttbrt.digres.domain.dto.ErrorsDTO;
import com.mttbrt.digres.domain.dto.resource.ErrorDTO;
import com.mttbrt.digres.domain.dto.resource.IResource;
import com.mttbrt.digres.domain.dto.resource.SourceDTO;
import java.util.ArrayList;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

public class PreprocessingHelper {

  public static ResponseEntity<?> castRequest(String source, DataDTO obj, Class<?> expectedClass) {
    IResource resource = obj.getData();

    if (!expectedClass.isInstance(resource)) {
      return ResponseEntity.badRequest().body(
          createError(HttpStatus.BAD_REQUEST.value(), REQUEST_ERROR, "Request not valid.",
              "The request is not valid.", source));
    }

    return null;
  }

  public static ResponseEntity<?> handleValidationErrors(String source, Errors errors) {
    ArrayList<ErrorDTO> errorsRes = new ArrayList<>();
    errors.getFieldErrors().forEach(err -> errorsRes.add(
        new ErrorDTO(HttpStatus.BAD_REQUEST.value(), JSON_VALIDATION_ERROR,
            "Incorrect request parameters.",
            "Field '" + err.getField() + "' " + err.getDefaultMessage(), new SourceDTO(source))));
    return ResponseEntity.badRequest().body(new ErrorsDTO(errorsRes));
  }

  public static ErrorsDTO createError(int status, ErrorCode code, String title, String details,
      String source) {
    ErrorDTO error = new ErrorDTO(status, code, title, details, new SourceDTO(source));
    return new ErrorsDTO(Collections.singletonList(error));
  }

}
