package com.alquimiasoft.com.minegocio.exceptionsHandlers;

import com.alquimiasoft.com.minegocio.dto.response.ErrorResponse;
import com.alquimiasoft.com.minegocio.exceptions.RequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<ErrorResponse> handleException(RequestException ex) {
        logger.error(ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(ex.getStatus().value(), ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }

}
