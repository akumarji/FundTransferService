package com.banking.service.FundTransferService.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Data
public class ErrorResponse {

    private String errorMessage;
    private HttpStatus errorCode;;
    private LocalDateTime timestamp;
}
