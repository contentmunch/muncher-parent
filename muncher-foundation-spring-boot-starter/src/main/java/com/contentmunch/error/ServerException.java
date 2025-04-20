package com.contentmunch.error;

public class ServerException extends BaseException {
    public ServerException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public ServerException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
