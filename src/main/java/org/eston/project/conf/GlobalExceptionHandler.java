package org.eston.project.conf;

import org.eston.project.conf.exception.BadRequestException;
import org.eston.project.entity.vo.BasicResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理类
 * @author shiyingchen
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 处理入参异常
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleCustomException(BadRequestException ex) {
        // 打印异常信息
        log.error("Bad-Request: {}", ex.getMessage());
        // 返回自定义异常信息和 400 状态码
        return new ResponseEntity<>(BasicResp.fail(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleInvalidException(MethodArgumentNotValidException ex) {
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> errors.put(e.getField(),e.getDefaultMessage()));
        // 打印异常信息
        log.error("Bad-Request: {}", errors);
        // 返回自定义异常信息和 400 状态码
        return new ResponseEntity<>(BasicResp.fail(errors.toString()), HttpStatus.BAD_REQUEST);
    }

    // 处理其他异常
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        // 打印异常信息
        log.error(ex.getMessage());
        // 返回通用错误信息和 500 状态码
        return new ResponseEntity<>(BasicResp.fail("An error occurred. Please try again later."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
