package com.banking.service.FundTransferService.controller;

import com.banking.service.FundTransferService.enums.Currency;
import com.banking.service.FundTransferService.model.dto.AccountResponseDto;
import com.banking.service.FundTransferService.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = AccountController.class)
@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountService accountsService;

    @Test
    void testGetAccountDetails() throws Exception {
        AccountResponseDto accountResponseDto = AccountResponseDto.builder()
                .accountId(1l)
                .balance(new BigDecimal("100"))
                .currency(Currency.INR).build();

        Mockito.when(accountsService.getAccountDetails(1L))
                .thenReturn(accountResponseDto);

        mockMvc.perform(get("/api/accounts/{accountId}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(accountsService, Mockito.times(1)).getAccountDetails(any(Long.class));
    }
}