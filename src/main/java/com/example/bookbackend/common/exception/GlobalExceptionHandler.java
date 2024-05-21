package com.example.bookbackend.common.exception;
import com.example.bookbackend.common.response.CommonResponse;
import com.example.bookbackend.common.response.ApiCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity handle(GlobalException exception, HttpServletRequest request) {
        String exceptionSource = extractExceptionSource(exception);
        CommonResponse<?> errorCode = new CommonResponse<>(exception.getApiCode().getCode(), exception.getApiCode().getMsg(), exception.getApiCode().getStatus());
        log.warn(
                "source = {} \n {} = {} \n code = {} \n message = {} \n info = {}",
                exceptionSource,
                request.getMethod(), request.getRequestURI(),
                errorCode.getCode(), errorCode.getMessage(),
                errorCode.getInfo().toString()
        );
        return ResponseEntity.ofNullable(new CommonResponse(errorCode.getCode(), errorCode.getMessage()));
    }
    private String extractExceptionSource(Exception exception) {
        StackTraceElement[] stackTrace = exception.getStackTrace();
        if (stackTrace.length > 0) {
            return stackTrace[0].toString();
        }
        return "Unknown Source";
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleServerException(Exception exception, HttpServletRequest request) {
        String exceptionSource = extractExceptionSource(exception);
        CommonResponse<?> errorCode = new CommonResponse<>(ApiCode.API_9999.getCode(), ApiCode.API_9999.getCode());
        log.error(
                "source = {} \n {} = {}",
                exceptionSource,
                request.getMethod(), request.getRequestURI(),
                exception
        );
        return ResponseEntity.ofNullable(new CommonResponse(errorCode.getCode(), errorCode.getMessage()));
    }

    /**
     *  Bean Validation 예외핸들러
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {
        log.error("validation exception :: ", e);

        Map<String, String> errors = errorsToMap(e);

        return ResponseEntity.badRequest()
                .body(CommonResponse.from(ApiCode.API_9000, errors));
    }

    private Map<String, String> errorsToMap(MethodArgumentNotValidException e) {
        return e.getBindingResult().getAllErrors().stream()
                .collect(Collectors.toMap(
                        error -> getField(error),
                        error -> error.getDefaultMessage()
                ));
    }

    private String getField(ObjectError error) {
        if (error instanceof FieldError) {
            return ((FieldError) error).getField();
        }

        return error.getObjectName();
    }
}