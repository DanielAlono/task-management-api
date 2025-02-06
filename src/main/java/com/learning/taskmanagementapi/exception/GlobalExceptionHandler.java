package com.learning.taskmanagementapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleAllExceptions(Exception e) {
		return new ResponseEntity<>("An error ocurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<String> handleTaskNotFound(TaskNotFoundException e) {
		return new ResponseEntity<>("Task not found: " + e.getMessage(), HttpStatus.NOT_FOUND);
	}
}
