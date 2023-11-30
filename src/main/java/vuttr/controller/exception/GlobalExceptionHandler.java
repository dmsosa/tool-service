package vuttr.controller.exception;


import jakarta.annotation.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;
import vuttr.controller.exception.tool.ToolExistsException;
import vuttr.controller.exception.tool.ToolNotFoundException;
import vuttr.domain.ApiError;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiError> exceptionHandler(Exception exception, WebRequest request) {
        HttpHeaders headers = new HttpHeaders(); //set Headers


        if (exception instanceof ToolExistsException) {
            HttpStatus status = HttpStatus.CONFLICT;
            ToolExistsException tee = (ToolExistsException) exception;
            return ToolExistsExceptionHandler(tee, headers, status, request);
        } else if (exception instanceof ToolNotFoundException) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            ToolNotFoundException tnfe = (ToolNotFoundException) exception;
            return ToolNotFoundExceptionHandler(tnfe, headers, status, request);
        }
        else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            return internalExceptionHandler(exception, null, headers, status, request);
        }
    }
    protected ResponseEntity<ApiError> ToolExistsExceptionHandler(ToolExistsException tee, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> error = Collections.singletonList(tee.getMessage());
        return internalExceptionHandler(tee, new ApiError(error), headers, status, request);
    }
    protected ResponseEntity<ApiError> ToolNotFoundExceptionHandler(ToolNotFoundException tnfe, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> error = Collections.singletonList(tnfe.getMessage());
        return internalExceptionHandler(tnfe, new ApiError(error), headers, status, request);
    }
    protected ResponseEntity<ApiError> internalExceptionHandler(Exception exception, @Nullable ApiError body,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {
        if (status.equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, exception, WebRequest.SCOPE_REQUEST);
        }
        return new ResponseEntity<>(body, headers, status);
    }
}
