package de.c24.finacc.klt.services;

import de.c24.finacc.klt.domain.Data;
import de.c24.finacc.klt.domain.Details;
import de.c24.finacc.klt.domain.Exchange;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RateServiceTest {
    private final static Double BASE_AMOUNT = 2d;
    private final static String BASECURRENCY = "USD";
    private final static String TARGET_CURRENCY = "RUB";
    private final static String URL = "https://api.currencyapi.com/v3/latest?apikey=";
    private final static String KEY = "bbYjqtVfn8WXgD4LtUR4dKL9M6fnc6LUhD2YSr25";
    private final static String BASE_CURRENCY = "&base_currency=";
    private final static String CURRENCY = "&currencies=";
    private final static Double TARGET_AMOUNT_PER_ONE = 33.0;
    @InjectMocks
    private RateServiceImpl rateService;
    @Mock
    private Builder builder;
    @Mock
    private Builder builder2;
    @Mock
    private WebClient webClient;
    @Mock
    private Exchange exchange;
    @Mock
    private Data data;
    @Mock
    private RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private RequestBodySpec requestBodySpec;
    @Mock
    private ResponseSpec responseSpec;
    @Mock
    private Mono mono;
    @Mock
    private Details details;

    @Test
    public void rateServiceTest() {
        when(builder.baseUrl(URL + KEY + BASE_CURRENCY + BASECURRENCY + CURRENCY + TARGET_CURRENCY)).thenReturn(builder2);
        when(builder2.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/")).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Exchange.class)).thenReturn(mono);
        when(mono.block()).thenReturn(exchange);
        when(exchange.getData()).thenReturn(data);
        when(data.getProperty(TARGET_CURRENCY)).thenReturn(details);
        when(details.getValue()).thenReturn(TARGET_AMOUNT_PER_ONE);

        Double result = rateService.convert(BASE_AMOUNT, BASECURRENCY, TARGET_CURRENCY);
        assertThat(result).isEqualTo(BASE_AMOUNT * TARGET_AMOUNT_PER_ONE);
    }
}