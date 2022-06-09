package com.mttbrt.digres.dto.response.validation;

import com.mttbrt.digres.dto.response.ResponseDTO;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ResponseDTOValidator implements ConstraintValidator<ValidResponseDTO, ResponseDTO> {

  @Override
  public void initialize(ValidResponseDTO constraintAnnotation) {
  }

  @Override
  public boolean isValid(ResponseDTO responseDTO, ConstraintValidatorContext constraintValidatorContext) {
    return responseDTO.getData() == null ^ responseDTO.getError() == null;
  }
}
