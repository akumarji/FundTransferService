package com.banking.service.FundTransferService.service;

import com.banking.service.FundTransferService.model.TransferRequest;
import com.banking.service.FundTransferService.model.dto.AccountResponseDto;

public interface AccountService {

    AccountResponseDto getAccountDetails(Long accountId);

    void transferMoney(TransferRequest request);
}
