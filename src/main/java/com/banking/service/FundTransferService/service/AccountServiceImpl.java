package com.banking.service.FundTransferService.service;

import com.banking.service.FundTransferService.enums.Currency;
import com.banking.service.FundTransferService.exception.AccountNotFoundException;
import com.banking.service.FundTransferService.exception.CardNotFoundException;
import com.banking.service.FundTransferService.exception.IncorrectCurrencyException;
import com.banking.service.FundTransferService.exception.OverDraftException;
import com.banking.service.FundTransferService.model.TransferRequest;
import com.banking.service.FundTransferService.model.dto.AccountResponseDto;
import com.banking.service.FundTransferService.model.entity.Account;
import com.banking.service.FundTransferService.repository.AccountRepository;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountsRepository;

    private final ExchangeRateService exchangeRateService;

    public AccountResponseDto getAccountDetails(Long accountId) {
        log.info("Fetching account details from repository");
        Account account = accountsRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account with id:" + accountId + " does not exist."));
        return AccountResponseDto.builder().accountId(account.getAccountId()).balance(account.getBalance()).currency(account.getCurrency()).build();
    }

    @Transactional
    @Retryable(retryFor = OptimisticLockException.class, maxAttempts = 2, backoff = @Backoff(delay = 1000))
    public void transferMoney(TransferRequest request) {
        log.info("Fetching accountFrom details from repository for transaction");
        Account accountFrom = accountsRepository.findById(request.getAccountFrom().getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account with id:" + request.getAccountFrom().getAccountId() + " does not exist."));
        validateAccountFromBalance(request, accountFrom);
        validateAccountFromCard(accountFrom);
        validateAccountFromCurrency(request, accountFrom.getCurrency());


        log.info("Fetching accountTo details from repository for transaction");
        Account accountTo = accountsRepository.findById(request.getAccountTo().getAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Account with id:" + request.getAccountTo().getAccountId() + " does not exist."));
        validateAccountToCard(accountTo);
        validateAccountToCurrency(request, accountTo.getCurrency());

        BigDecimal exchangeRate = exchangeRateService.getExchangeRate(accountFrom.getCurrency(), accountTo.getCurrency());
        updateAccountBalances(request, accountFrom, accountTo, exchangeRate);
    }


    private void validateAccountFromBalance(TransferRequest request, Account accountFrom) {
        log.info("Validating accountFrom balance");
        if (accountFrom.getBalance().compareTo(request.getAmount()) < 0) {
            throw new OverDraftException("Account with id:" + accountFrom.getAccountId() + " does not have enough balance to transfer.");
        }
    }

    private void validateAccountFromCurrency(TransferRequest request, Currency currencyFrom) {
        log.info("Validating accountFrom currency");
        if (currencyFrom != request.getAccountFrom().getCurrency()) {
            throw new IncorrectCurrencyException
                    ("Account with id:" + request.getAccountFrom().getAccountId() + " is not matching currency: "
                            + request.getAccountFrom().getCurrency() + " with actual currency: " + currencyFrom);
        }
    }

    private void validateAccountFromCard(Account accountFrom) {
        log.info("Validating accountFrom cards");
        if (!accountFrom.isDebitCard() || !accountFrom.isCreditCard()) {
            throw new CardNotFoundException("Account with Id:" + accountFrom.getAccountId() + " is not having debit or credit card.");
        }
    }

    private void validateAccountToCurrency(TransferRequest request, Currency currencyTo) {
        log.info("Validating accountTo currency");
        if (currencyTo != request.getAccountTo().currency) {
            throw new IncorrectCurrencyException
                    ("Account with id:" + request.getAccountTo().getAccountId() + " is not matching currency: "
                            + request.getAccountTo().getCurrency() + " with actual currency: " + currencyTo);
        }
    }

    private void validateAccountToCard(Account accountTo) {
        log.info("Validating accountTo cards");
        if (!accountTo.isDebitCard() || !accountTo.isCreditCard()) {
            throw new CardNotFoundException("account with Id:" + accountTo.getAccountId() + " is not having debit or credit card.");
        }
    }

    private void updateAccountBalances(TransferRequest request, Account accountFrom, Account accountTo, BigDecimal exchangeRate) {
        log.info("update account balances");
        accountFrom.setBalance(accountFrom.getBalance().subtract(request.getAmount()));
        accountTo.setBalance(accountTo.getBalance().add(request.getAmount().multiply(exchangeRate)));
        accountsRepository.save(accountFrom);
        accountsRepository.save(accountTo);
    }

}
