package com.banking.service.FundTransferService.repository;

import com.banking.service.FundTransferService.enums.Currency;
import com.banking.service.FundTransferService.model.entity.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    Account account = Account.builder()
            .accountId(1L)
            .balance(new BigDecimal("1000"))
            .currency(Currency.EUR)
            .creditCard(true)
            .debitCard(true)
            .version(1)
            .build();

    @BeforeEach
    public void setUp() {
        accountRepository.save(account);
    }

    @AfterEach
    public void tearDown() {
        accountRepository.delete(account);
    }

    @Test
    public void testFindById() {
        Optional<Account> accountReceived = accountRepository.findById(1L);
        assertEquals(account, accountReceived.get());
    }

}
