package com.contentmunch.error;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public BaseException(ErrorMessage errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }
    public BaseException(ErrorMessage errorMessage, Throwable cause) {
        super(cause);
        this.errorMessage = errorMessage;
    }

}
