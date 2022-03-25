package de.c24.finacc.klt.services;

import de.c24.finacc.klt.dto.ExchangeDTO;

public interface RateService {

    ExchangeDTO getExchange(Double baseAmount, String baseCurrency, String targetCurrency);
}