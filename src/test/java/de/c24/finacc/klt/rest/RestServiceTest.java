package de.c24.finacc.klt.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * RestServiceTest
 */
class RestServiceTest {

    @Mock
    de.c24.finacc.klt.services.RateServiceImpl rateService;

    private final RestService restService = new RestService(rateService);

    @Test
    @DisplayName("Simple test")
    void convertTest() {
        //Map<String, String> result = restService.convert();
        //assertThat(result).isNotEmpty();
    }
}
