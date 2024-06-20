package com.banking.service.FundTransferService.controller;

import com.banking.service.FundTransferService.model.dto.AccountResponseDto;
import com.banking.service.FundTransferService.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.banking.service.FundTransferService.constants.Constants.API_ACCOUNTS;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(API_ACCOUNTS)
public class AccountController {

    private final AccountService accountService;

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", content = {@Content(mediaType = "application/json")}, description = "Account Not Found"),
            @ApiResponse(responseCode = "500", content = {@Content(mediaType = "application/json")}, description = "Internal Server Error")})
    @Operation(summary = "API to fetch account details",
            description = "Fetching account details based on accountId")
    @GetMapping(value = "/{accountId}", produces = {"application/json"})
    public ResponseEntity<AccountResponseDto> getAccountDetails(@PathVariable Long accountId) {
        log.info("Executing getAccountDetails request for account id {}", accountId);
        AccountResponseDto account = accountService.getAccountDetails(accountId);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}

