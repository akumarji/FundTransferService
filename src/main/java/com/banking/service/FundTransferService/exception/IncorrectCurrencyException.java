package com.banking.service.FundTransferService.exception;

public class IncorrectCurrencyException extends RuntimeException {

    public IncorrectCurrencyException(String message) {
        super(message);
    }

    public IncorrectCurrencyException(String message, Throwable cause) {
        super(message, cause);
    }
}
