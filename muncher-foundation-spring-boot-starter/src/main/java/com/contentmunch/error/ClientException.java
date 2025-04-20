package com.contentmunch.error;

public class ClientException extends BaseException {
    public ClientException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public ClientException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
