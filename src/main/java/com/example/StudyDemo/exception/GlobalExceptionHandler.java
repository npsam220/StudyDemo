package com.example.StudyDemo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
        LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 🔴 業務エラー（商品が見つからない場合）
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleProductNotFound(ProductNotFoundException ex) {

        log.error("業務エラー: {}", ex.getMessage());

        return new ErrorResponse("404", ex.getMessage());
    }

    // 🔴 業務エラー（社員が見つからない場合）
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEmployeeNotFound(EmployeeNotFoundException ex) {

        log.error("業務エラー: {}", ex.getMessage());

        return new ErrorResponse("404", ex.getMessage());
    }

    // 🔴 システムエラー
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneral(Exception ex) {

        log.error("システムエラー", ex);

        return new ErrorResponse("500", "システムエラーが発生しました");
    }

    // 🔴 404（URL不存在）
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handle404(NoHandlerFoundException ex) {

        log.warn("404エラー: {}", ex.getRequestURL());

        return new ErrorResponse("404", "該当するURLが存在しません");
    }

    // 🔴 400（バリデーションエラー）
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex) {

        return new ErrorResponse("400", "パラメータエラーが発生しました");
    }
}