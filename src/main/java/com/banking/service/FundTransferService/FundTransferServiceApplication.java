package com.banking.service.FundTransferService;

import com.banking.service.FundTransferService.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class FundTransferServiceApplication {

    @Autowired
    AccountRepository accountsRepository;

    public static void main(String[] args) {
        SpringApplication.run(FundTransferServiceApplication.class, args);
    }

}
