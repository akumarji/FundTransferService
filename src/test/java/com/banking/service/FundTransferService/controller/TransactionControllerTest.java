package com.banking.service.FundTransferService.controller;

import com.banking.service.FundTransferService.enums.Currency;
import com.banking.service.FundTransferService.model.TransferRequest;
import com.banking.service.FundTransferService.model.dto.AccountRequestDto;
import com.banking.service.FundTransferService.service.AccountService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static com.banking.service.FundTransferService.constants.Constants.API_TRANSACTIONS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = TransactionController.class)
@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountService accountsService;

    @Test
    void testTransferMoney() throws Exception {
        AccountRequestDto accountFrom = AccountRequestDto.builder()
                .accountId(1L)
                .currency(Currency.EUR)
                .build();
        AccountRequestDto accountTo = AccountRequestDto.builder()
                .accountId(2L)
                .currency(Currency.INR)
                .build();

        TransferRequest transferRequest = TransferRequest.builder()
                .accountFrom(accountFrom)
                .accountTo(accountTo)
                .amount(new BigDecimal("1000")).
                build();

        Gson gson = new Gson();
        String payload = gson.toJson(transferRequest);

        doNothing().when(accountsService).transferMoney(any(TransferRequest.class));

        mockMvc.perform(post(API_TRANSACTIONS)
                        .content(payload)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(accountsService, Mockito.times(1)).transferMoney(any(TransferRequest.class));
    }
}
