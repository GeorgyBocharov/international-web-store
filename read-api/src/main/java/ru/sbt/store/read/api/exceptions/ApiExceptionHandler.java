package ru.sbt.store.read.api.exceptions;

import brave.Span;
import brave.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final Tracer tracer;

    @ExceptionHandler
    protected ResponseEntity<Object> handleError(ReadApiException ex, WebRequest request) {
        logger.error("Exception occurred", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorResponse.builder()
                        .errorCode(HttpStatus.BAD_REQUEST.name())
                        .errorMessage(ex.getMessage())
                        .errorId(getCurrentTraceId())
                        .build());
    }

    private String getCurrentTraceId() {
        Span span = tracer.currentSpan();
        if (span != null) {
            return span.context().traceIdString();
        } else {
            return "";
        }
    }
}