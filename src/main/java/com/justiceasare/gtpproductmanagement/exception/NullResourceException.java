package com.justiceasare.gtpproductmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class NullResourceException extends RuntimeException {

    public NullResourceException(String message) {
        super(message);
    }
}
