package com.supportportal.exception.domain;

public class CourseNameAlreadyExistsException extends RuntimeException  {
    public CourseNameAlreadyExistsException(String message) {
        super(message);
    }
}
