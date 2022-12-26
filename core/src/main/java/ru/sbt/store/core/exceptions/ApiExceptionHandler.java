package ru.sbt.store.core.exceptions;

import brave.Span;
import brave.Tracer;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final Tracer tracer;

    @ExceptionHandler
    protected ResponseEntity<Object> handleError(OnlineStoreException ex, WebRequest request) {
        logger.error("Exception occurred", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiErrorResponse.builder()
                        .errorCode(HttpStatus.BAD_REQUEST.name())
                        .errorMessage(ex.getMessage())
                        .errorId(getCurrentTraceId())
                        .build());
    }

    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request) {
        List<ApiErrorResponse> errorResponses = new ArrayList<>(ex.getFieldErrors().size());
        for (FieldError error : ex.getFieldErrors()) {
            errorResponses.add(ApiErrorResponse.builder()
                    .errorCode(HttpStatus.BAD_REQUEST.name())
                    .errorMessage(String.format("Name %s - %s", error.getField(), error.getDefaultMessage()))
                    .errorId(getCurrentTraceId())
                    .build());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponses);
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
