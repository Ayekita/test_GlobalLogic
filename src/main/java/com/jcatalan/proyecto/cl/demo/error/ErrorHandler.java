package com.jcatalan.proyecto.cl.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorInfo>> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {

        // get spring errors
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<ErrorInfo> outErrors = new ArrayList<>();
        
        for (FieldError f : fieldErrors) {
			String message = f.getField() + ":" + f.getDefaultMessage();
			//outErrors.add(HttpStatus.BAD_REQUEST, fieldErrors);
		}
        
        
        return new ResponseEntity<>(outErrors, HttpStatus.BAD_REQUEST);

    }

}
