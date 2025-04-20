package com.contentmunch.error;

public class BusinessException extends BaseException {
    public BusinessException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public BusinessException(ErrorMessage errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }


}
