package ru.vsu.sheluhin.carService.exeption;

import ru.vsu.sheluhin.carService.response.ErrorCode;

public class ValidationException extends RuntimeException {

    ErrorCode errorCode;

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public ValidationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ValidationException(String message) {
        super(message);
    }
}
