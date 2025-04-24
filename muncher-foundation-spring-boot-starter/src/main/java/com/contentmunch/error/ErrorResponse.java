package com.contentmunch.error;

import lombok.Builder;

@Builder
public record ErrorResponse(String errorMessage, String errorCode) {
}
