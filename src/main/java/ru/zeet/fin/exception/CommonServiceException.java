package ru.zeet.fin.exception;

public class CommonServiceException extends RuntimeException {
    public CommonServiceException(Throwable cause) {
        super(cause);
    }

    public CommonServiceException(String message) {
        super(message);
    }
}
