package com.mttbrt.digres.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.mttbrt.digres.dto.response.ResponseDTO;
import com.mttbrt.digres.dto.response.error.ErrorResDTO;
import com.mttbrt.digres.dto.response.error.SingleErrorResDTO;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  @ResponseBody
  public ResponseDTO handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e, HttpServletRequest req) {
    ArrayList<SingleErrorResDTO> errors = new ArrayList<>();
    e.getBindingResult().getFieldErrors().forEach(err -> errors.add(
        new SingleErrorResDTO(err.getDefaultMessage(), err.getField())));

    ErrorResDTO error = new ErrorResDTO(BAD_REQUEST.value(),
        "Incorrect request parameters.", errors);
    return new ResponseDTO(error);
  }

  @ExceptionHandler(value = NotFoundException.class)
  @ResponseStatus(value = NOT_FOUND)
  @ResponseBody
  public ResponseDTO handleNotFoundException(NotFoundException e) {
    SingleErrorResDTO singleError = new SingleErrorResDTO(e.getErr().error, e.getLocation());
    ErrorResDTO error = new ErrorResDTO(NOT_FOUND.value(),
        e.getErr().message, List.of(singleError));
    return new ResponseDTO(error);
  }

  @ExceptionHandler(value = FoundException.class)
  @ResponseStatus(value = BAD_REQUEST)
  @ResponseBody
  public ResponseDTO handleFoundException(FoundException e) {
    SingleErrorResDTO singleError = new SingleErrorResDTO(e.getErr().error, e.getLocation());
    ErrorResDTO error = new ErrorResDTO(BAD_REQUEST.value(),
        e.getErr().message, List.of(singleError));
    return new ResponseDTO(error);
  }

}
