package com.course.courseselection.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CustomApiError {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<SubApiError> subApiErrors;

    public CustomApiError() {
        timestamp = LocalDateTime.now();
    }

    public CustomApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    public CustomApiError(HttpStatus status, Exception exception) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = exception.getLocalizedMessage();
    }

    CustomApiError(HttpStatus status, String message, Exception exception) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = exception.getLocalizedMessage();
    }

    void addValidationError(MethodArgumentNotValidException ex) {

        ex.getBindingResult().getAllErrors().stream()
                .map(objectError -> (FieldError) objectError)
                .map(fieldError -> new ApiValidationError(
                        fieldError.getObjectName(),
                        fieldError.getField(),
                        fieldError.getRejectedValue(),
                        fieldError.getDefaultMessage()))
                .forEach(this::addSubError);
    }

    private void addSubError(SubApiError subApiError) {
        if (subApiErrors == null) {
            subApiErrors = new ArrayList<>();
        }

        subApiErrors.add(subApiError);
    }
}
