package com.banking.service.FundTransferService.model.dto;

import com.banking.service.FundTransferService.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountResponseDto {

    public Long accountId;

    public Currency currency;

    private BigDecimal balance;
}
