package com.virginonline.mediasoft.web.rest;

import com.virginonline.mediasoft.domain.exception.ArticleAlreadyExist;
import com.virginonline.mediasoft.domain.exception.ExceptionBody;
import com.virginonline.mediasoft.domain.exception.ProductNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomControllerAdvice {

  @ExceptionHandler(ProductNotFound.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionBody handleResourceNotFound(
      final ProductNotFound e
  ) {
    return new ExceptionBody(e.getMessage());
  }

  @ExceptionHandler(ArticleAlreadyExist.class)
  @ResponseStatus(HttpStatus.BAD_GATEWAY)
  public ExceptionBody handleResourceNotFound(
      final ArticleAlreadyExist e
  ) {
    return new ExceptionBody(e.getMessage());
  }

  @ExceptionHandler(IllegalStateException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ExceptionBody handleIllegalState(final IllegalStateException e) {
    return new ExceptionBody(e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ExceptionBody handleException(final Exception e) {
    e.printStackTrace();
    return new ExceptionBody("Internal error");
  }
}
