package com.banking.service.FundTransferService.model.entity;


import com.banking.service.FundTransferService.enums.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "ACCOUNTS")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ACCOUNT_ID")
    public Long accountId;

    @NotNull
    @Min(value = 0, message = "account balance must be positive")
    @Column(name = "BALANCE")
    private BigDecimal balance;

    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY")
    public Currency currency;

    @Column(name = "DEBIT_CARD")
    public boolean debitCard;

    @Column(name = "CREDIT_CARD")
    public boolean creditCard;

    @Column(name = "VERSION")
    @Version
    private int version;
}
