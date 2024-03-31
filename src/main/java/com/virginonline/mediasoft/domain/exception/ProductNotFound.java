package com.virginonline.mediasoft.domain.exception;

public class ProductNotFound extends RuntimeException {
  public ProductNotFound(String message) {
    super(message);
  }
}
