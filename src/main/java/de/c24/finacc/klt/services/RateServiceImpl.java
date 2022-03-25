package de.c24.finacc.klt.services;

import de.c24.finacc.klt.domain.Data;
import de.c24.finacc.klt.domain.Exchange;
import de.c24.finacc.klt.dto.ExchangeDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import java.util.Objects;

@Service
public class RateServiceImpl implements RateService {
    private final Builder builder;

    String url = "https://api.currencyapi.com/v3/latest?apikey=";
    String key = "bbYjqtVfn8WXgD4LtUR4dKL9M6fnc6LUhD2YSr25";
    String base_currency = "&base_currency=";
    String currencies = "&currencies=";

    public RateServiceImpl(WebClient.Builder builder) {
        this.builder = builder;
    }

    @Override
    public ExchangeDTO getExchange(
            Double baseAmount,
            String baseCurrency,
            String targetCurrency
    ) {
        WebClient webClient = buildWebClient(baseCurrency, targetCurrency);
        Data data = getData(webClient);
        return assembleExchangeDTO(data, baseAmount, targetCurrency);
    }

    private WebClient buildWebClient(
            String baseCurrency,
            String targetCurrency
    ) {
        return builder
                .baseUrl(url + key + base_currency + baseCurrency + currencies + targetCurrency)
                .build();
    }

    //todo maybe send unused a parameter baseCurrency+targetCurrency as unique key for @Cacheable
    // because webClient is very big for key
    @Cacheable(cacheNames = "data", key = "#webClient")
    private Data getData(WebClient webClient) {
        return Objects.requireNonNull(webClient
                        .get()
                        .uri("/")
                        .retrieve()
                        .bodyToMono(Exchange.class)
                        .block())
                .getData();
    }

    private ExchangeDTO assembleExchangeDTO(
            Data data,
            Double baseAmount,
            String targetCurrency
    ) {
        Double targetAmount = data.getProperty(targetCurrency).getValue();
        ExchangeDTO exchangeDTO = new ExchangeDTO(baseAmount);
        exchangeDTO.setCurrency(targetCurrency);
        exchangeDTO.setAmount(targetAmount);

        return exchangeDTO;
    }
}