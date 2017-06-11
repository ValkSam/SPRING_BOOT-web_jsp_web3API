package com.example.demo.dao.exception;

/**
 * Created by ValkSam on 11.06.2017.
 */
public class NotFountException extends RuntimeException {
  public NotFountException() {
  }

  public NotFountException(String message) {
    super(message);
  }
}
