package com.example.StudyDemo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
        LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 🔴 業務エラー（社員が見つからない場合）
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEmployeeNotFound(EmployeeNotFoundException ex) {

        log.error("業務エラー: {}", ex.getMessage());

        return new ErrorResponse("404", ex.getMessage());
    }

    // 🔴 業務エラー（社員が見つからない場合）
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneral(Exception ex) {

        log.error("システムエラー", ex);

        return new ErrorResponse("500", "システムエラーが発生しました");
    }
   
}