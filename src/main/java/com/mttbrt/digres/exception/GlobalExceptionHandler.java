package com.mttbrt.digres.exception;

import static com.mttbrt.digres.domain.ErrorCode.JSON_VALIDATION_ERROR;

import com.mttbrt.digres.domain.dto.ErrorsDTO;
import com.mttbrt.digres.domain.dto.ResponseDTO;
import com.mttbrt.digres.domain.dto.resource.ErrorDTO;
import com.mttbrt.digres.domain.dto.resource.SourceDTO;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  public ResponseDTO handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e, HandlerMethod handlerMethod) {
    RequestMapping reqMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
    String path = reqMapping != null ? Arrays.toString(reqMapping.path()) : "";

    ArrayList<ErrorDTO> errors = new ArrayList<>();
    e.getBindingResult().getFieldErrors().forEach(err -> errors.add(
        new ErrorDTO(HttpStatus.BAD_REQUEST.value(), JSON_VALIDATION_ERROR,
            "Incorrect request parameters.",
            "Field '" + err.getField() + "' " + err.getDefaultMessage(), new SourceDTO(path))));

    return new ErrorsDTO(errors);
  }

}
