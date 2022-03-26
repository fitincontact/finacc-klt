package de.c24.finacc.klt.rest;

import de.c24.finacc.klt.KltApplication;
import de.c24.finacc.klt.services.RateServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = KltApplication.class)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class RestServiceTest {
    private final static Double BASE_AMOUNT = 2d;
    private final static String BASE_CURRENCY = "USD";
    private final static String TARGET_CURRENCY = "RUB";
    @Captor
    ArgumentCaptor<Double> baseAmountCaptor;
    @Captor
    ArgumentCaptor<String> baseCurrencyCaptor;
    @Captor
    ArgumentCaptor<String> targetCurrencyCaptor;
    @Autowired
    private MockMvc mvc;
    @Mock
    private RateServiceImpl rateService;
    @InjectMocks
    private RestService restService;

    @Test
    @DisplayName("contract test")
    void contractTest() {
        final var amount = 23.0;

        when(rateService.convert(BASE_AMOUNT, BASE_CURRENCY, TARGET_CURRENCY)).thenReturn(amount);
        Double result = restService.convert(BASE_AMOUNT, BASE_CURRENCY, TARGET_CURRENCY);
        assertThat(result).isEqualTo(amount);

        verify(rateService).convert(baseAmountCaptor.capture(), baseCurrencyCaptor.capture(), targetCurrencyCaptor.capture());
        assertThat(BASE_AMOUNT).isEqualTo(baseAmountCaptor.getValue());
        assertThat(BASE_CURRENCY).isEqualTo(baseCurrencyCaptor.getValue());
        assertThat(TARGET_CURRENCY).isEqualTo(targetCurrencyCaptor.getValue());

        verify(rateService).convert(BASE_AMOUNT, BASE_CURRENCY, TARGET_CURRENCY);
    }

    @Test
    @DisplayName("status test")
    void statusTest() throws Exception {
        mvc.perform(
                        get(
                                "/api/latest/{baseAmount}/{baseCurrency}/{targetCurrency}",
                                BASE_AMOUNT,
                                BASE_CURRENCY,
                                TARGET_CURRENCY
                        )
                )
                .andExpect(status().isOk());
    }
}