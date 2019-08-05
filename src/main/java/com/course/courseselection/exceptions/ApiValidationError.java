package com.course.courseselection.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
class ApiValidationError implements SubApiError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;
}
