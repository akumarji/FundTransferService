package com.banking.service.FundTransferService.exception;

public class OverDraftException extends RuntimeException {

    public OverDraftException(String message) {
        super(message);
    }

    public OverDraftException(String message, Throwable cause) {
        super(message, cause);
    }
}
