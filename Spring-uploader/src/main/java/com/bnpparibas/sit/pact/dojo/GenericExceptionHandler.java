package com.bnpparibas.sit.pact.dojo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 *
 *
 */
@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * used to handle size exceed exception
     *
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity handleGlobalException(HttpServletRequest request, Throwable ex) {
        return new ResponseEntity(ex.getMessage(), getStatus(request));
    }


    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
