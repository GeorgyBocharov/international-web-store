package ru.sbt.store.read.api.exceptions;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiErrorResponse {

    private final String errorCode;
    private final String errorMessage;
    private final String errorId;
}
