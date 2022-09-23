package com.dtsgroup.userservice.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class BusinessException extends Exception {

    private String message;
    private HttpStatus httpStatus;

    public BusinessException(String message, HttpStatus httpStatus) {
        super();
        this.message = message;
        this.httpStatus = httpStatus;
    }

}
