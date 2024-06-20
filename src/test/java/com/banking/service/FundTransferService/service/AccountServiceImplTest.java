package com.banking.service.FundTransferService.service;

import com.banking.service.FundTransferService.enums.Currency;
import com.banking.service.FundTransferService.model.TransferRequest;
import com.banking.service.FundTransferService.model.dto.AccountRequestDto;
import com.banking.service.FundTransferService.model.dto.AccountResponseDto;
import com.banking.service.FundTransferService.model.entity.Account;
import com.banking.service.FundTransferService.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    ExchangeRateService exchangeRateService;

    @InjectMocks
    AccountServiceImpl accountService;

    @Test
    void testGetAccountDetails() {
        Account account = Account.builder()
                .accountId(1L)
                .balance(new BigDecimal("100"))
                .currency(Currency.EUR)
                .creditCard(true)
                .creditCard(true)
                .build();

        Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        AccountResponseDto result = accountService.getAccountDetails(1L);

        assertEquals(account.getAccountId(), result.getAccountId());
        assertEquals(account.getBalance(), result.getBalance());
        assertEquals(account.getCurrency(), result.getCurrency());

        Mockito.verify(accountRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void testTransferMoney() {

        Account accountFrom = Account.builder()
                .accountId(1L)
                .balance(new BigDecimal("1000"))
                .currency(Currency.EUR)
                .creditCard(true)
                .debitCard(true)
                .build();
        Account accountTo = Account.builder()
                .accountId(2L)
                .balance(new BigDecimal("1000"))
                .currency(Currency.INR)
                .creditCard(true)
                .debitCard(true)
                .build();

        AccountRequestDto from = AccountRequestDto.builder()
                .accountId(1L)
                .currency(Currency.EUR)
                .build();
        AccountRequestDto to = AccountRequestDto.builder()
                .accountId(2L)
                .currency(Currency.INR)
                .build();

        TransferRequest transferRequest = TransferRequest.builder()
                .accountFrom(from)
                .accountTo(to)
                .amount(new BigDecimal("100")).
                build();

        Mockito.when(accountRepository.findById(from.getAccountId())).thenReturn(Optional.of(accountFrom));
        Mockito.when(accountRepository.findById(to.getAccountId())).thenReturn(Optional.of(accountTo));
        Mockito.when(accountRepository.save(accountFrom)).thenReturn(accountFrom);
        Mockito.when(accountRepository.save(accountTo)).thenReturn(accountTo);
        Mockito.when(exchangeRateService.getExchangeRate(Currency.EUR, Currency.INR)).thenReturn(new BigDecimal("90"));

        accountService.transferMoney(transferRequest);
        Mockito.verify(accountRepository, Mockito.times(2)).findById(any(Long.class));
        Mockito.verify(exchangeRateService, Mockito.times(1)).getExchangeRate(any(Currency.class), any(Currency.class));
    }

}


