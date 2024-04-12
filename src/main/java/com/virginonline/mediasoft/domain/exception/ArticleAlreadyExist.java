package com.virginonline.mediasoft.domain.exception;

public class ArticleAlreadyExist extends RuntimeException {

  public ArticleAlreadyExist(String message) {
    super(message);
  }
}
