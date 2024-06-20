package com.banking.service.FundTransferService.controller;

import com.banking.service.FundTransferService.model.TransferRequest;
import com.banking.service.FundTransferService.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.banking.service.FundTransferService.constants.Constants.API_TRANSACTIONS;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(API_TRANSACTIONS)
public class TransactionController {

    private final AccountService accountService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Account Not Found"),
            @ApiResponse(responseCode = "500", description = "Exchange Rate Not Found")})
    @Operation(summary = "API to create transaction",
            description = "Create transaction with exchange rate")
    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<Void> transferMoney(@RequestBody @Valid TransferRequest request) {
        log.info("Executing transfer request");
        accountService.transferMoney(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
