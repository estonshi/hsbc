package org.eston.project.conf.exception;

/**
 * 接口入参异常
 * @author shiyingchen
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
