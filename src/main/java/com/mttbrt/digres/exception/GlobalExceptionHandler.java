package com.mttbrt.digres.exception;

import com.mttbrt.digres.dto.response.error.ErrorResDTO;
import com.mttbrt.digres.dto.response.ResponseDTO;
import com.mttbrt.digres.dto.response.error.SingleErrorResDTO;
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

    ArrayList<SingleErrorResDTO> errors = new ArrayList<>();
    e.getBindingResult().getFieldErrors().forEach(err -> errors.add(
        new SingleErrorResDTO(err.getDefaultMessage(), err.getField())));

    ErrorResDTO error = new ErrorResDTO(HttpStatus.BAD_REQUEST.value(), "Incorrect request parameters.",
        errors);
    return new ResponseDTO(error);
  }

}
