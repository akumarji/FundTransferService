package com.banking.service.FundTransferService.model.dto;

import com.banking.service.FundTransferService.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountRequestDto {

    @NotNull
    public Long accountId;

    @NotBlank
    public Currency currency;
}
