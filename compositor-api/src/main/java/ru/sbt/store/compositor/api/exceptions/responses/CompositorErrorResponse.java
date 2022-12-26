package ru.sbt.store.compositor.api.exceptions.responses;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CompositorErrorResponse {

    private final String errorCode;
    private final String errorMessage;
    private final String errorId;
}
