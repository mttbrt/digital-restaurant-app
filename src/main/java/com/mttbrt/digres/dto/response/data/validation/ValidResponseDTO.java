package com.mttbrt.digres.dto.response.data.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = {ResponseDTOValidator.class})
public @interface ValidResponseDTO {

  String message() default "Please provide either 'data' or 'error' (not both).";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
