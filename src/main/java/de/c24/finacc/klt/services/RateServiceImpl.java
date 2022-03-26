package de.c24.finacc.klt.services;

import de.c24.finacc.klt.domain.Data;
import de.c24.finacc.klt.domain.Exchange;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import java.util.Objects;

@Service
public class RateServiceImpl implements RateService {
    private final static String URL = "https://api.currencyapi.com/v3/latest?apikey=";
    private final static String KEY = "bbYjqtVfn8WXgD4LtUR4dKL9M6fnc6LUhD2YSr25";
    private final static String BASE_CURRENCY = "&base_currency=";
    private final static String CURRENCIES = "&currencies=";

    private final Builder builder;

    public RateServiceImpl(WebClient.Builder builder) {
        this.builder = builder;
    }

    @Override
    public Double getExchange(
            Double baseAmount,
            String baseCurrency,
            String targetCurrency
    ) {
        WebClient webClient = buildWebClient(baseCurrency, targetCurrency);
        Data data = getData(webClient);
        return data.getProperty(targetCurrency).getValue() * baseAmount;
    }

    private WebClient buildWebClient(
            String baseCurrency,
            String targetCurrency
    ) {
        return builder
                .baseUrl(URL + KEY + BASE_CURRENCY + baseCurrency + CURRENCIES + targetCurrency)
                .build();
    }

    //todo maybe send unused a parameter baseCurrency+targetCurrency as unique key for @Cacheable
    // because webClients is very big objects for keys
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
}