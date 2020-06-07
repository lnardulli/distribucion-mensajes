package com.test.masvoz.exceptions;

import com.test.masvoz.model.response.ProviderResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(PrefixNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ProviderResponseError handleNoRecordFoundException(PrefixNotFoundException ex) {

        log.error(ex.getMessage());
        return ProviderResponseError.builder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage()).build();
    }
}
