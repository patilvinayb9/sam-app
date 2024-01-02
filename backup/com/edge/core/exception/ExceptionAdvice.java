package com.edge.core.exception;

import com.edge.core.modules.common.EdgeResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public EdgeResponse<String> handleMaxSizeException(
            MaxUploadSizeExceededException ex) {
        return EdgeResponse.createErrorResponse(ex.getMessage(), null, null, null);
    }
}