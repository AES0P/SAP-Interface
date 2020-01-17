package com.aesop.demo.rfcclient.infra.exception;

import org.springframework.stereotype.Component;

@Component
public class SapException extends RuntimeException {

    public SapException() {
        super();
    }

    public SapException(String message) {
        super(message);
    }

    public SapException(Throwable cause) {
        super(cause);
    }

    public SapException(String message, Throwable cause) {
        super(message, cause);
    }


}
