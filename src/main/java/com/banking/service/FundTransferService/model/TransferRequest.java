package com.banking.service.FundTransferService.model;

import com.banking.service.FundTransferService.model.dto.AccountRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransferRequest {

    @NotNull
    private AccountRequestDto accountFrom;

    @NotNull
    private AccountRequestDto accountTo;

    @NotNull
    @Min(value = 0, message = "account balance must be positive")
    private BigDecimal amount;
}
