package com.mttbrt.digres.exception;

import com.mttbrt.digres.dto.response.ErrorDTO;
import com.mttbrt.digres.dto.response.ResponseDTO;
import com.mttbrt.digres.dto.response.SingleErrorDTO;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ResponseDTO handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e, HttpServletRequest req) {

    ArrayList<SingleErrorDTO> errors = new ArrayList<>();
    e.getBindingResult().getFieldErrors().forEach(err -> errors.add(
        new SingleErrorDTO("'" + err.getField() + "' " + err.getDefaultMessage())));

    ErrorDTO error = new ErrorDTO(HttpStatus.BAD_REQUEST.value(), "Incorrect request parameters.",
        errors);
    return new ResponseDTO(error);
  }

}
