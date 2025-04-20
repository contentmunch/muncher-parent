package com.contentmunch.authentication.error;

import com.contentmunch.error.ClientException;
import com.contentmunch.error.ErrorMessage;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ClientException {

    public UserNotFoundException() {

        super(ErrorMessage.builder()
                .message("User not found")
                .code(AuthErrorCode.USER_NOT_FOUND.name())
                .status(HttpStatus.FORBIDDEN)
                .build());
    }
}
