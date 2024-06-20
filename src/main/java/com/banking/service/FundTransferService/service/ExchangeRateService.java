package com.banking.service.FundTransferService.service;

import com.banking.service.FundTransferService.enums.Currency;
import com.banking.service.FundTransferService.exception.ExchangeRateNotFoundException;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static com.banking.service.FundTransferService.constants.Constants.EXCHANGE_RATE_API;
import static com.banking.service.FundTransferService.constants.Constants.RATES;

@AllArgsConstructor
@Service
public class ExchangeRateService {

    private final RestTemplate restTemplate;

    public BigDecimal getExchangeRate(Currency currencyFrom, Currency currencyTo) {

        try {
            String response = restTemplate.getForEntity
                    (EXCHANGE_RATE_API + currencyFrom, String.class).getBody();

            JSONObject exchangeRates = new JSONObject(response);
            JSONObject rates = exchangeRates.getJSONObject(RATES);
            return rates.getBigDecimal(currencyTo.toString());
        } catch (Exception e) {
            throw new ExchangeRateNotFoundException("Error occured while fetching exchange rate from " + currencyFrom + " to " + currencyTo);
        }
    }
}
