package com.example.hello.exception;

// AlreadyExists.java
/**
 * Exception thrown when a resource already exists in the system.
 * For example, when trying to add a student with an email that already exists.
 */
public class AlreadyExists extends RuntimeException {
  public AlreadyExists(String message) {
    super(message);
  }
}